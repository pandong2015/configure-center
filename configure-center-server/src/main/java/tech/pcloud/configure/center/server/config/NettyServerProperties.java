package tech.pcloud.configure.center.server.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @ClassName ConfigServerProperties
 * @Author pandong
 * @Date 2018/9/17 16:17
 **/
@ConfigurationProperties(prefix = "configure.center.netty")
public class NettyServerProperties {
    private int port;
    private String publicKey;
    private String privateKey;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }
}
