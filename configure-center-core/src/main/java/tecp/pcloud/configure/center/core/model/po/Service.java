package tecp.pcloud.configure.center.core.model.po;

/**
 * @ClassName Service
 * @Author pandong
 * @Date 2018/9/28 13:41
 **/
public class Service extends DefaultPropertiesImpl {
    private String privateKey;
    private String publicKey;
    private Long userId;

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
