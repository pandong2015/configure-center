package tech.pcloud.configure.center.server.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tech.pcloud.configure.center.netty.handler.DHSecurityCodecHandler;
import tech.pcloud.configure.center.netty.handler.DSASecurityCodecHandler;
import tech.pcloud.configure.center.server.config.NettyServerProperties;
import tech.pcloud.configure.center.server.server.handler.ServerChannelHandler;
import tecp.pcloud.configure.center.protocol.CCProtocol;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @ClassName ConfigServer
 * @Author pandong
 * @Date 2018/9/17 16:12
 **/
@Component
public class ConfigServer {
    private static final Logger log = LoggerFactory.getLogger(ConfigServer.class);
    @Autowired
    private NettyServerProperties properties;
    @Autowired
    private ManagerFactory managerFactory;
    @Autowired
    private ServerPublicKeyFactory serverPublicKeyFactory;
    private EventLoopGroup bossGroup = new NioEventLoopGroup();
    private EventLoopGroup workerGroup = new NioEventLoopGroup();
    private ServerBootstrap bootstrap;

    @PostConstruct
    public void init() {
        bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel channel) throws Exception {
                        channel.pipeline().addLast(new ProtobufVarint32FrameDecoder());
                        channel.pipeline().addLast(new ProtobufVarint32LengthFieldPrepender());
                        channel.pipeline().addLast(new DHSecurityCodecHandler());
                        channel.pipeline().addLast(new ProtobufDecoder(CCProtocol.Protocol.getDefaultInstance()));
                        channel.pipeline().addLast(new ProtobufEncoder());
                        channel.pipeline().addLast(new DSASecurityCodecHandler(properties.getPrivateKey(), serverPublicKeyFactory));
                        channel.pipeline().addLast(new ServerChannelHandler(managerFactory));
                    }
                });
        try {
            log.info("config center server start on port " + properties.getPort());
            bootstrap.bind(properties.getPort()).get();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    @PreDestroy
    public void destroy() {
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }
}
