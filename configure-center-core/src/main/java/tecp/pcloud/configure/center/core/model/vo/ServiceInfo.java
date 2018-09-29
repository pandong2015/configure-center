package tecp.pcloud.configure.center.core.model.vo;

import tecp.pcloud.configure.center.core.model.po.Service;
import tecp.pcloud.configure.center.core.model.po.User;

/**
 * @ClassName ServiceInfo
 * @Author pandong
 * @Date 2018/9/28 13:51
 **/
public class ServiceInfo {
    private Service service;
    private User user;

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
