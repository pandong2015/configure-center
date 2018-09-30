package tech.pcloud.configure.center.client.handler;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.pcloud.configure.center.client.Client;
import tecp.pcloud.configure.center.protocol.CCProtocol;

/**
 * @ClassName ClientChannelHandler
 * @Author pandong
 * @Date 2018/9/18 9:28
 **/
public class ClientChannelHandler extends SimpleChannelInboundHandler<CCProtocol.Protocol> {
    private static final Logger log = LoggerFactory.getLogger(ClientChannelHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, CCProtocol.Protocol protocol) throws Exception {
        log.debug("get response: " + protocol.toString());
        Client.setProtocol(protocol);
        channelHandlerContext.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
    }
}
