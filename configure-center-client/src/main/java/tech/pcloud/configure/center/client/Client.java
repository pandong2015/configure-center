package tech.pcloud.configure.center.client;

import com.google.protobuf.ByteString;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.pcloud.configure.center.client.config.ClientProperties;
import tech.pcloud.configure.center.client.handler.ClientChannelHandler;
import tech.pcloud.configure.center.client.util.Cache;
import tech.pcloud.configure.center.netty.handler.DHSecurityCodecHandler;
import tech.pcloud.configure.center.netty.handler.DSASecurityCodecHandler;
import tecp.pcloud.configure.center.core.exception.ConnectionException;
import tecp.pcloud.configure.center.core.model.po.Configure;
import tecp.pcloud.configure.center.core.util.CCProtocolUtil;
import tecp.pcloud.configure.center.protocol.CCProtocol;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ClassName CLient
 * @Author pandong
 * @Date 2018/9/18 9:18
 **/
public class Client {
    private static final Logger log = LoggerFactory.getLogger(Client.class);
    private static Cache cache = new Cache();

    private ClientProperties properties;
    private Bootstrap bootstrap;
    private NioEventLoopGroup workerGroup = new NioEventLoopGroup();

    public Client(ClientProperties properties) {
        System.setProperty("jdk.crypto.KeyAgreement.legacyKDF", "true");
        this.properties = properties;
        this.bootstrap = createBootstrap();
    }

    private Bootstrap createBootstrap() {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(workerGroup);
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel channel) throws Exception {
                channel.pipeline().addLast(new ProtobufVarint32FrameDecoder());
                channel.pipeline().addLast(new ProtobufVarint32LengthFieldPrepender());
                channel.pipeline().addLast(new DHSecurityCodecHandler());
                channel.pipeline().addLast(new ProtobufDecoder(CCProtocol.Protocol.getDefaultInstance()));
                channel.pipeline().addLast(new ProtobufEncoder());
                channel.pipeline().addLast(new DSASecurityCodecHandler(
                        properties.getPrivateKey(),
                        new ClientPublicKeyFactory(properties.getPublicKey())));
                channel.pipeline().addLast(new ClientChannelHandler());
            }
        });
        return bootstrap;
    }

    public void connection(String... profiles) {
        connection(Arrays.stream(profiles).collect(Collectors.toList()));
    }

    public void connection(List<String> profiles) {
        ChannelFuture channelFuture = null;
        try {
            channelFuture = bootstrap.connect(properties.getHost(), properties.getPort()).sync();
        } catch (InterruptedException e) {
            log.warn("connection fail, " + e.getMessage(), e);
            throw new ConnectionException("connection fail, " + e.getMessage(), e);
        }
        if (channelFuture.isSuccess()) {
            log.info("connect success.");
            CCProtocol.ProtocolHeader.Builder headerBuild = CCProtocol.ProtocolHeader.newBuilder();
            headerBuild.addHeaders(CCProtocol.DataEntry.newBuilder()
                    .setName(ByteString.copyFromUtf8(CCProtocolUtil.HEADER_SERVICE_NAME))
                    .setValue(ByteString.copyFromUtf8(properties.getService()))
                    .build());
            profiles.stream()
                    .map(p -> {
                        CCProtocol.DataEntry dataEntry = CCProtocol.DataEntry.newBuilder()
                                .setName(ByteString.copyFromUtf8(CCProtocolUtil.HEADER_PROFILE_NAME))
                                .setValue(ByteString.copyFromUtf8(p))
                                .build();
                        return dataEntry;
                    }).forEach(d -> headerBuild.addHeaders(d));
            CCProtocol.Protocol protocol = CCProtocol.Protocol.newBuilder()
                    .setHeader(headerBuild)
                    .build();
            channelFuture.channel().writeAndFlush(protocol);
        } else {
            log.warn("connect fail.");
            channelFuture.channel().writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
            throw new ConnectionException("connect fail.");
        }
    }

    public static void setProtocol(CCProtocol.Protocol protocol) {
        CCProtocol.ProtocolBody body = protocol.getBody();
        cache.setProtocol(body);
    }

    public List<Configure> getConfigures() {
        CCProtocol.ProtocolBody body = null;
        while ((body = cache.getProtocol()) == null) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
        }
        return body.getPropertiesList().stream()
                .map(propertyEntry -> {
                    Configure configure = new Configure();
                    CCProtocol.DataEntry dataEntry = propertyEntry.getData();
                    configure.setName(dataEntry.getName().toStringUtf8());
                    if (ByteString.EMPTY.equals(dataEntry.getValue())) {
                        configure.setValue(null);
                    } else {
                        configure.setValue(dataEntry.getValue().toStringUtf8());
                    }
                    configure.setType(propertyEntry.getType().name());
                    return configure;
                }).collect(Collectors.toList());
    }

    public Map<String, Object> getConfigureMapping() {
        CCProtocol.ProtocolBody body = null;
        while ((body = cache.getProtocol()) == null) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
        }
        Map<String, Object> mapping = new HashMap<String, Object>();
        body.getPropertiesList()
                .forEach(propertyEntry -> {
                    CCProtocol.DataEntry dataEntry = propertyEntry.getData();
                    if (ByteString.EMPTY.equals(dataEntry.getValue())) {
                        mapping.put(dataEntry.getName().toStringUtf8(), null);
                    } else {
                        mapping.put(dataEntry.getName().toStringUtf8(),
                                dataEntry.getValue().toStringUtf8());
                    }
                });
        return mapping;
    }

    public static void main(String[] args) {
        ClientProperties properties = new ClientProperties();
        properties.setHost("127.0.0.1");
        properties.setPort(9902);
        properties.setService("test");
        properties.setPrivateKey("MIIBTAIBADCCASwGByqGSM44BAEwggEfAoGBAP1/U4EddRIpUt9KnC7s5Of2EbdSPO9EAMMeP4C2USZpRV1AIlH7WT2NWPq/xfW6MPbLm1Vs14E7gB00b/JmYLdrmVClpJ+f6AR7ECLCT7up1/63xhv4O1fnxqimFQ8E+4P208UewwI1VBNaFpEy9nXzrith1yrv8iIDGZ3RSAHHAhUAl2BQjxUjC8yykrmCouuEC/BYHPUCgYEA9+GghdabPd7LvKtcNrhXuXmUr7v6OuqC+VdMCz0HgmdRWVeOutRZT+ZxBxCBgLRJFnEj6EwoFhO3zwkyjMim4TwWeotUfI0o4KOuHiuzpnWRbqN/C/ohNWLx+2J6ASQ7zKTxvqhRkImog9/hWuWfBpKLZl6Ae1UlZAFMO/7PSSoEFwIVAIfgflNWPn69EkbnVLtN9MxXnpmX");
        properties.setPublicKey("MIIBuDCCASwGByqGSM44BAEwggEfAoGBAP1/U4EddRIpUt9KnC7s5Of2EbdSPO9EAMMeP4C2USZpRV1AIlH7WT2NWPq/xfW6MPbLm1Vs14E7gB00b/JmYLdrmVClpJ+f6AR7ECLCT7up1/63xhv4O1fnxqimFQ8E+4P208UewwI1VBNaFpEy9nXzrith1yrv8iIDGZ3RSAHHAhUAl2BQjxUjC8yykrmCouuEC/BYHPUCgYEA9+GghdabPd7LvKtcNrhXuXmUr7v6OuqC+VdMCz0HgmdRWVeOutRZT+ZxBxCBgLRJFnEj6EwoFhO3zwkyjMim4TwWeotUfI0o4KOuHiuzpnWRbqN/C/ohNWLx+2J6ASQ7zKTxvqhRkImog9/hWuWfBpKLZl6Ae1UlZAFMO/7PSSoDgYUAAoGBAI8gRDWAG8EBKFTFjXTe3KD0Q8BhukU1Z0bZ0k6eFmIilmRyMRa0A0R4XkBVVkzlXoaMryG9/BsVsmnX49yFGtdCGUUDwX81mayneiYd8Eu6Hv2HV84v6Edh4tvYdbEqqkQIsI0MNO1easxJD+XyRucnapeW3NGdXzl9nxow09wO");

        Client client = new Client(properties);

        client.connection("default", "web", "dev");
    }

    class ClientPublicKeyFactory implements DSASecurityCodecHandler.PublicKeyFactory {
        private String publicKey;

        public ClientPublicKeyFactory(String publicKey) {
            this.publicKey = publicKey;
        }

        @Override
        public String getPublicKey(String serviceName) {
            return publicKey;
        }
    }
}
