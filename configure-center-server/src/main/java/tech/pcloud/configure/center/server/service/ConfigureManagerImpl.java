package tech.pcloud.configure.center.server.service;

import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.pcloud.configure.center.server.mapper.ConfigureMapper;
import tech.pcloud.configure.center.server.mapper.ProfileMapper;
import tech.pcloud.configure.center.server.mapper.ServiceMapper;
import tecp.pcloud.configure.center.core.exception.NoDataException;
import tecp.pcloud.configure.center.core.model.po.Configure;
import tecp.pcloud.configure.center.core.model.po.Profile;
import tecp.pcloud.configure.center.core.model.vo.ConfigureInfo;
import tecp.pcloud.configure.center.core.model.vo.request.SearchPeametersRequest;
import tecp.pcloud.configure.center.core.model.vo.request.parameter.ConfigureSearchParameters;
import tecp.pcloud.configure.center.core.model.vo.response.ConfigureResponse;
import tecp.pcloud.configure.center.core.model.vo.response.ConfiguresResponse;
import tecp.pcloud.configure.center.core.service.ConfigureManager;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ClassName ConfigureManagerImpl
 * @Author pandong
 * @Date 2018/9/29 9:37
 **/
@Service
public class ConfigureManagerImpl implements ConfigureManager {
    @Autowired
    private ConfigureMapper configureMapper;
    @Autowired
    private ProfileMapper profileMapper;
    @Autowired
    private ServiceMapper serviceMapper;

    @Override
    public void insert(Configure configure) {
        configureMapper.insert(configure);
    }

    @Override
    public void update(Configure configure) {
        configureMapper.update(configure);
    }

    @Override
    public void delete(Configure configure) {
        configureMapper.delete(configure);
    }

    @Override
    public ConfiguresResponse select(SearchPeametersRequest<ConfigureSearchParameters> request) {
        int count = configureMapper.selectCount(request);
        Map<Long, Profile> profileMap = Maps.newHashMap();
        Map<Long, tecp.pcloud.configure.center.core.model.po.Service> serviceMap = Maps.newHashMap();
        serviceMapper.allActive().forEach(s -> serviceMap.put(s.getId(), s));
        profileMapper.allActive().forEach(p -> profileMap.put(p.getId(), p));

        List<ConfigureResponse> data = configureMapper.select(request).stream()
                .map(c -> {
                    ConfigureResponse configureResponse = new ConfigureResponse();
                    configureResponse.setName(c.getName());
                    configureResponse.setValue(c.getValue());
                    configureResponse.setProfileId(c.getProfileId());
                    if (profileMap.containsKey(c.getProfileId())) {
                        configureResponse.setProfileName(profileMap.get(c.getProfileId()).getName());
                    }
                    configureResponse.setServiceId(c.getServiceId());
                    if (serviceMap.containsKey(c.getServiceId())) {
                        configureResponse.setServiceName(serviceMap.get(c.getServiceId()).getName());
                    }
                    configureResponse.setType(c.getType());
                    configureResponse.setStatus(c.getStatus());
                    configureResponse.setCreateTime(c.getCreateTime());
                    configureResponse.setUpdateTime(c.getUpdateTime());
                    return configureResponse;
                }).collect(Collectors.toList());
        ConfiguresResponse response = new ConfiguresResponse();
        response.setList(data);
        response.setRequest(request);
        response.setTotalSize(count);
        return response;
    }

    @Override
    public ConfigureInfo load(long id) {
        Configure configure = configureMapper.load(id);
        if (configure == null) {
            throw new NoDataException("data is not exist.");
        }
        ConfigureInfo configureInfo = new ConfigureInfo();
        configureInfo.setConfigure(configure);
        configureInfo.setProfile(profileMapper.load(configure.getProfileId()));
        configureInfo.setService(serviceMapper.load(configure.getServiceId()));
        return configureInfo;
    }

    @Override
    public void copy(long sourceServiceId, long targetServiceId) {
        configureMapper.copyServiceConfig(sourceServiceId, targetServiceId);
    }

    @Override
    public void copy(long sourceServiceId, long targetServiceId, long targetProfileId) {
        configureMapper.copyServiceConfigToProfile(sourceServiceId, targetServiceId, targetProfileId);
    }

    @Override
    public void copy(long sourceServiceId, long sourceProfileId, long targetServiceId, long targetProfileId) {
        configureMapper.copyServiceAndProfileConfig(sourceServiceId, sourceProfileId, targetServiceId, targetProfileId);
    }

}
