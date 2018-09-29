package tecp.pcloud.configure.center.core.model.po;

/**
 * @ClassName Role
 * @Author pandong
 * @Date 2018/9/28 13:19
 **/
public class Token extends DefaultPropertiesImpl {
    private String token;
    private long userId;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
