package tecp.pcloud.configure.center.core.model.vo;

import com.google.common.collect.Lists;
import tecp.pcloud.configure.center.core.model.po.Role;
import tecp.pcloud.configure.center.core.model.po.Service;
import tecp.pcloud.configure.center.core.model.po.Token;
import tecp.pcloud.configure.center.core.model.po.User;

import java.util.List;

/**
 * @ClassName UserInfo
 * @Author pandong
 * @Date 2018/9/28 13:50
 **/
public class UserInfo {
    private User user;
    private Token token;
    private List<Role> roles = Lists.newArrayList();
    private List<Service> services = Lists.newArrayList();

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public Role getRole(int index) {
        return roles.get(index);
    }

    public void addRole(Role role) {
        this.roles.add(role);
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public int getRoleSize() {
        return roles.size();
    }

    public List<Service> getServices() {
        return services;
    }

    public Service getService(int index) {
        return services.get(index);
    }

    public void addService(Service service) {
        this.services.add(service);
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

    public int getServiceSize() {
        return services.size();
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }
}
