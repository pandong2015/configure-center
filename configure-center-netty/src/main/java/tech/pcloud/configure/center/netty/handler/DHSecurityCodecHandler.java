package tech.pcloud.configure.center.netty.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.ByteToMessageCodec;
import io.netty.util.AttributeKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tecp.pcloud.configure.center.core.util.security.DHKeyPair;
import tecp.pcloud.configure.center.core.util.security.DHUtil;

import java.util.Base64;
import java.util.List;

/**
 * @ClassName DHSecurityCodecHandler
 * @Author pandong
 * @Date 2018/9/17 15:07
 **/
public class DHSecurityCodecHandler extends ByteToMessageCodec<ByteBuf> {
    private static final Logger log = LoggerFactory.getLogger(DHSecurityCodecHandler.class);
    private static final AttributeKey<Short> SHAKE = AttributeKey.newInstance("shake");
    private static final AttributeKey<byte[]> CONTENT = AttributeKey.newInstance("content");
    private static final AttributeKey<byte[]> PUBLIC_KEY = AttributeKey.newInstance("publuc-key");
    private static final AttributeKey<byte[]> PRIVATE_KEY = AttributeKey.newInstance("private-key");

    @Override
    protected void encode(ChannelHandlerContext ctx, ByteBuf msg, ByteBuf out) throws Exception {
        Short shake = ctx.channel().attr(SHAKE).get();

        byte[] data = new byte[msg.readableBytes()];
        msg.readBytes(data);

        if (shake == null) {
            shake = 0;
        }

        switch (shake) {
            //client, publicKey == null && privateKey == null
            case 0:
                log.debug("shake " + shake + ", create client key pair");
                DHKeyPair keyPair = DHUtil.initKey();
                ctx.channel().attr(PRIVATE_KEY).set(keyPair.getPrivateKey().getEncoded());
                byte[] publicKey = keyPair.getPublicKey().getEncoded();
                log.debug("shake " + shake + ", sent client public key: " + Base64.getEncoder().encodeToString(publicKey));
                out.writeBytes(keyPair.getPublicKey().getEncoded());
                log.debug("shake " + shake + ", cache first sent content: " + Base64.getEncoder().encodeToString(data));
                ctx.channel().attr(CONTENT).set(data);
                shake = 1;
                ctx.channel().attr(SHAKE).set(shake);
                break;

            //server send public key, publicKey != null && privateKey != null
            case 1:
                shake = 2;
                ctx.channel().attr(SHAKE).set(shake);
                log.debug("shake " + shake + ", send public key: " + Base64.getEncoder().encodeToString(data));
                out.writeBytes(data);
                break;

            //client send content, publicKey != null && privateKey != null
            case 2:
                log.debug("shake " + shake + ", send data, encrypt content: " + Base64.getEncoder().encodeToString(data));
                try{
                    byte[] encrypt = DHUtil.encrypt(data,
                            Base64.getEncoder().encodeToString(ctx.channel().attr(PUBLIC_KEY).get()),
                            Base64.getEncoder().encodeToString(ctx.channel().attr(PRIVATE_KEY).get()));
                    out.writeBytes(encrypt);
                }catch (Exception e){
                    log.error(e.getMessage());
                    throw e;
                }
                break;
        }
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        Short shake = ctx.channel().attr(SHAKE).get();

        byte[] data = new byte[in.readableBytes()];
        in.readBytes(data);

        if (shake == null) {
            shake = 0;
        }

        switch (shake) {
            //server , publicKey == null && privateKey == null
            case 0:
                log.debug("shake " + shake + ", create server key pair, client public key: " + Base64.getEncoder().encodeToString(data));
                ctx.channel().attr(PUBLIC_KEY).set(data);
                log.debug("shake " + shake + ", create server key pair");
                DHKeyPair keyPair = DHUtil.initKey(data);
                ctx.channel().attr(PRIVATE_KEY).set(keyPair.getPrivateKey().getEncoded());
                shake = 1;
                ctx.channel().attr(SHAKE).set(shake);
                ctx.channel().writeAndFlush(Unpooled.copiedBuffer(keyPair.getPublicKey().getEncoded()));
                break;

            //client, publicKey == null && privateKey != null
            case 1:
                log.debug("shake " + shake + ", cache server public key");
                ctx.channel().attr(PUBLIC_KEY).set(data);
                log.debug("shake " + shake + ", send cache data");
                shake = 2;
                ctx.channel().attr(SHAKE).set(shake);
                byte[] content = ctx.channel().attr(CONTENT).get();
                ctx.channel().attr(CONTENT).set(null);
                if (content != null) {
                    log.debug("shake " + shake + ", sent content: " + Base64.getEncoder().encodeToString(content));
                    ctx.channel().writeAndFlush(Unpooled.copiedBuffer(content));
                }
                break;

            //server decrypt content
            case 2:
                log.debug("shake " + shake + ", load data, decrypt content: " + Base64.getEncoder().encodeToString(data));
                try{
                    byte[] decrypt = DHUtil.decrypt(data,
                            ctx.channel().attr(PUBLIC_KEY).get(),
                            ctx.channel().attr(PRIVATE_KEY).get());
                    out.add(Unpooled.copiedBuffer(decrypt));
                }catch (Exception e){
                    log.error(e.getMessage());
                    throw e;
                }
                break;
        }
    }

    @Override
    public void disconnect(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
        ctx.channel().attr(PUBLIC_KEY).set(null);
        ctx.channel().attr(PRIVATE_KEY).set(null);
        ctx.channel().attr(SHAKE).set(null);
        super.disconnect(ctx, promise);
    }
}
