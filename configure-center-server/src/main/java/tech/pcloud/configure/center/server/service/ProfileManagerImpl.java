package tech.pcloud.configure.center.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.pcloud.configure.center.server.mapper.ProfileMapper;
import tecp.pcloud.configure.center.core.exception.NoDataException;
import tecp.pcloud.configure.center.core.model.po.Profile;
import tecp.pcloud.configure.center.core.model.vo.ProfileInfo;
import tecp.pcloud.configure.center.core.model.vo.request.SearchPeametersRequest;
import tecp.pcloud.configure.center.core.model.vo.request.parameter.NameSearchParameters;
import tecp.pcloud.configure.center.core.model.vo.response.DataRowResponse;
import tecp.pcloud.configure.center.core.model.vo.response.DataRowsResponse;
import tecp.pcloud.configure.center.core.model.vo.response.OptionResponse;
import tecp.pcloud.configure.center.core.service.ProfileManager;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName ProfileManagerImpl
 * @Author pandong
 * @Date 2018/9/29 0:10
 **/
@Service
public class ProfileManagerImpl implements ProfileManager {
    @Autowired
    private ProfileMapper profileMapper;

    @Override
    public DataRowsResponse select(SearchPeametersRequest<NameSearchParameters> request) {
        int count = profileMapper.selectCount(request);
        List<DataRowResponse> roles = profileMapper.select(request).stream()
                .map(r -> {
                    DataRowResponse dataRowResponse = new DataRowResponse();
                    dataRowResponse.setId(r.getId());
                    dataRowResponse.setName(r.getName());
                    dataRowResponse.setStatus(r.getStatus());
                    dataRowResponse.setCreateTime(r.getCreateTime());
                    dataRowResponse.setUpdateTime(r.getUpdateTime());
                    return dataRowResponse;
                }).collect(Collectors.toList());
        DataRowsResponse response = new DataRowsResponse();
        response.setList(roles);
        response.setRequest(request);
        response.setTotalSize(count);
        return response;
    }

    @Override
    public void insert(Profile profile) {
        profileMapper.insert(profile);
    }

    @Override
    public void update(Profile profile) {
        profileMapper.update(profile);
    }

    @Override
    public void delete(long id) {
        profileMapper.delete(id);
    }

    @Override
    public ProfileInfo load(long id) {
        Profile profile = profileMapper.load(id);
        if(profile==null){
            throw new NoDataException("data is not exist.");
        }
        ProfileInfo profileInfo = new ProfileInfo();
        profileInfo.setProfile(profile);
        return profileInfo;
    }

    @Override
    public List<OptionResponse> allActive() {
        return profileMapper.allActive().stream()
                .map(r -> {
                    OptionResponse dataRowResponse = new OptionResponse();
                    dataRowResponse.setId(r.getId());
                    dataRowResponse.setName(r.getName());
                    return dataRowResponse;
                }).collect(Collectors.toList());
    }
}
