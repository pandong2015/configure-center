package tech.pcloud.configure.center.server.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @ClassName ConfigServerProperties
 * @Author pandong
 * @Date 2018/9/17 16:17
 **/
@ConfigurationProperties(prefix = "configure.center")
public class AppProperties {
    private int accountTimeout;
    private String securityKey;
    private String charset;

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String getSecurityKey() {
        return securityKey;
    }

    public void setSecurityKey(String securityKey) {
        this.securityKey = securityKey;
    }

    public int getAccountTimeout() {
        return accountTimeout;
    }

    public void setAccountTimeout(int accountTimeout) {
        this.accountTimeout = accountTimeout;
    }
}
