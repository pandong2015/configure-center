package tech.pcloud.configure.center.server.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import tech.pcloud.configure.center.server.mapper.ServiceMapper;
import tech.pcloud.configure.center.server.mapper.UserMapper;
import tech.pcloud.configure.center.server.util.security.DSAUtil;
import tecp.pcloud.configure.center.core.exception.InitKeyException;
import tecp.pcloud.configure.center.core.exception.NoDataException;
import tecp.pcloud.configure.center.core.model.po.Service;
import tecp.pcloud.configure.center.core.model.po.User;
import tecp.pcloud.configure.center.core.model.vo.ServiceInfo;
import tecp.pcloud.configure.center.core.model.vo.request.SearchPeametersRequest;
import tecp.pcloud.configure.center.core.model.vo.request.parameter.ServiceSearchParameters;
import tecp.pcloud.configure.center.core.model.vo.response.OptionResponse;
import tecp.pcloud.configure.center.core.model.vo.response.ServiceResponse;
import tecp.pcloud.configure.center.core.model.vo.response.ServicesResponse;
import tecp.pcloud.configure.center.core.service.ServiceManager;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName ServiceManagerImpl
 * @Author pandong
 * @Date 2018/9/28 17:55
 **/
@org.springframework.stereotype.Service
public class ServiceManagerImpl implements ServiceManager {
    private static final Logger log = LoggerFactory.getLogger(ServiceManagerImpl.class);
    @Autowired
    private ServiceMapper serviceMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public void insert(Service service) {
        DSAUtil.DSAKeyPair keyPair = createKeyPair();
        service.setPrivateKey(keyPair.getPrivateKeyWithBase64());
        service.setPublicKey(keyPair.getPublicKeyWithBase64());
        serviceMapper.save(service);
    }

    @Override
    public void update(Service service) {
        serviceMapper.updateServiceName(service);
    }

    @Override
    public void updateKey(Service service) {
        DSAUtil.DSAKeyPair keyPair = createKeyPair();
        service.setPrivateKey(keyPair.getPrivateKeyWithBase64());
        service.setPublicKey(keyPair.getPublicKeyWithBase64());
        serviceMapper.updateServiceKey(service);
    }

    @Override
    public void delete(long id) {
        serviceMapper.delete(id);
    }

    @Override
    public ServicesResponse select(SearchPeametersRequest<ServiceSearchParameters> request) {
        int count = serviceMapper.selectCount(request);
        List<ServiceResponse> services = serviceMapper.select(request).stream()
                .map(s -> {
                    User user = userMapper.load(s.getUserId());
                    ServiceResponse serviceResponse = new ServiceResponse();
                    serviceResponse.setId(s.getId());
                    serviceResponse.setUserId(s.getUserId());
                    if (user != null) {
                        serviceResponse.setUserName(user.getName());
                    }
                    serviceResponse.setName(s.getName());
                    serviceResponse.setStatus(s.getStatus());
                    serviceResponse.setCreateTime(s.getCreateTime());
                    serviceResponse.setUpdateTime(s.getUpdateTime());
                    return serviceResponse;
                }).collect(Collectors.toList());
        ServicesResponse response = new ServicesResponse();
        response.setList(services);
        response.setRequest(request);
        response.setTotalSize(count);
        return response;
    }

    @Override
    public ServiceInfo load(long id) {
        Service service = serviceMapper.load(id);
        if (service == null) {
            throw new NoDataException("data is not exist.");
        }
        ServiceInfo serviceInfo = new ServiceInfo();
        serviceInfo.setService(service);
        serviceInfo.setUser(userMapper.load(service.getUserId()));
        return serviceInfo;
    }

    @Override
    public List<OptionResponse> allActive() {
        return serviceMapper.allActive().stream()
                .map(s -> {
                    OptionResponse serviceResponse = new OptionResponse();
                    serviceResponse.setId(s.getId());
                    serviceResponse.setName(s.getName());
                    return serviceResponse;
                }).collect(Collectors.toList());
    }

    private DSAUtil.DSAKeyPair createKeyPair() {
        DSAUtil.DSAKeyPair keyPair = null;
        try {
            keyPair = DSAUtil.initKey(DSAUtil.DEFAULT_SEED);
        } catch (Exception e) {
            log.error("create dsa key pair fail, " + e.getMessage(), e);
            throw new InitKeyException("create dsa key pair fail, " + e.getMessage(), e);
        }
        return keyPair;
    }
}
