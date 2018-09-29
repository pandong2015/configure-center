package tecp.pcloud.configure.center.core.model.po;

/**
 * @ClassName User
 * @Author pandong
 * @Date 2018/9/28 13:40
 **/
public class User extends DefaultPropertiesImpl {
    private String email;
    private String account;
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
