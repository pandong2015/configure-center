package tech.pcloud.configure.center.server.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tech.pcloud.configure.center.server.mapper.ProfileMapper;
import tech.pcloud.configure.center.server.mapper.ServiceMapper;
import tecp.pcloud.configure.center.core.service.ConfigureManager;

/**
 * @ClassName ManagerFactory
 * @Author pandong
 * @Date 2018/9/30 10:31
 **/
@Component
public class ManagerFactory {
    @Autowired
    private ConfigureManager configureManager;
    @Autowired
    private ServiceMapper serviceMapper;
    @Autowired
    private ProfileMapper profileMapper;

    public ConfigureManager getConfigureManager() {
        return configureManager;
    }

    public ServiceMapper getServiceMapper() {
        return serviceMapper;
    }

    public ProfileMapper getProfileMapper() {
        return profileMapper;
    }
}
