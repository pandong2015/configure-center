package tech.pcloud.configure.center.server.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tech.pcloud.configure.center.netty.handler.DSASecurityCodecHandler;
import tecp.pcloud.configure.center.core.model.po.Service;
import tecp.pcloud.configure.center.core.service.ServiceManager;

/**
 * @ClassName ServerPublicKeyFactory
 * @Author pandong
 * @Date 2018/9/30 10:03
 **/
@Component
public class ServerPublicKeyFactory implements DSASecurityCodecHandler.PublicKeyFactory {
    @Autowired
    private ServiceManager serviceManager;

    @Override
    public String getPublicKey(String serviceName) {
        Service service = serviceManager.loadByName(serviceName);
        return service.getPublicKey();
    }
}
