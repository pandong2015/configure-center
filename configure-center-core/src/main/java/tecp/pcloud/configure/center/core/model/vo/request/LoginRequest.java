/**
 *
 */
package tecp.pcloud.configure.center.core.model.vo.request;

import tecp.pcloud.configure.center.core.model.SearchParameters;

/**
 * @author pandong
 * @ClassName DataTableSearch
 * @Date 2018年9月19日 下午12:35:34
 */
public class LoginRequest implements SearchParameters {
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
