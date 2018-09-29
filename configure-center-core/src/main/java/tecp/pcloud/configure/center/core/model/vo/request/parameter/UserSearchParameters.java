package tecp.pcloud.configure.center.core.model.vo.request.parameter;

import tecp.pcloud.configure.center.core.model.vo.request.parameter.NameSearchParameters;

/**
 * @ClassName UserParameter
 * @Author pandong
 * @Date 2018/9/28 15:41
 **/
public class UserSearchParameters extends NameSearchParameters {
    private String email;
    private String account;

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
}
