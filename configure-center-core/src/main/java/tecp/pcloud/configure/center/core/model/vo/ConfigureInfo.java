package tecp.pcloud.configure.center.core.model.vo;

import tecp.pcloud.configure.center.core.model.po.Configure;
import tecp.pcloud.configure.center.core.model.po.Profile;
import tecp.pcloud.configure.center.core.model.po.Service;

/**
 * @ClassName ConfiguleInfo
 * @Author pandong
 * @Date 2018/9/28 13:58
 **/
public class ConfigureInfo {
    private Configure configure;
    private Service service;
    private Profile profile;

    public Configure getConfigure() {
        return configure;
    }

    public void setConfigure(Configure configure) {
        this.configure = configure;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }
}
