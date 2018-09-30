package tech.pcloud.configure.center.netty.handler;

import com.google.common.collect.Maps;
import com.google.protobuf.ByteString;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tecp.pcloud.configure.center.core.util.CCProtocolUtil;
import tecp.pcloud.configure.center.core.util.security.DSAUtil;
import tecp.pcloud.configure.center.protocol.CCProtocol;

import java.util.List;
import java.util.Map;

/**
 * @ClassName DHSecurityCodecHandler
 * @Author pandong
 * @Date 2018/9/17 15:07
 **/
public class DSASecurityCodecHandler extends MessageToMessageCodec<CCProtocol.Protocol, CCProtocol.Protocol> {
    private static final Logger log = LoggerFactory.getLogger(DSASecurityCodecHandler.class);
    private String privateKey;
    private PublicKeyFactory publicKeyFactory;

    public DSASecurityCodecHandler(String privateKey, PublicKeyFactory publicKeyFactory) {
        this.privateKey = privateKey;
        this.publicKeyFactory = publicKeyFactory;
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, CCProtocol.Protocol protocol, List<Object> list) throws Exception {
        String sign = DSAUtil.sign(protocol.getBody().toByteArray(), privateKey);
        CCProtocol.Protocol newProtocol = CCProtocol.Protocol.newBuilder()
                .setBody(protocol.getBody())
                .setHeader(protocol.getHeader())
                .setSignature(ByteString.copyFromUtf8(sign))
                .build();
        list.add(newProtocol);
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, CCProtocol.Protocol protocol, List<Object> list) throws Exception {
        if (protocol.getSerializedSize() == 0) {
            return;
        }
        CCProtocol.ProtocolHeader header = protocol.getHeader();
        Map<String, List<String>> headerMap = CCProtocolUtil.headerMap(header);

        if (DSAUtil.verify(protocol.getBody().toByteArray(),
                publicKeyFactory.getPublicKey(CCProtocolUtil.getHeaderValue(headerMap, CCProtocolUtil.HEADER_SERVICE_NAME)),
                protocol.getSignature().toStringUtf8())) {
            list.add(protocol);
        } else {
            log.warn("Signature verify fail.");
            channelHandlerContext.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
        }
    }


    public interface PublicKeyFactory {
        String getPublicKey(String serviceName);
    }
}
