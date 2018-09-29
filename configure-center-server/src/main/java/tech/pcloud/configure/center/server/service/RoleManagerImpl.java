package tech.pcloud.configure.center.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.pcloud.configure.center.server.mapper.RoleMapper;
import tech.pcloud.configure.center.server.mapper.UserMapper;
import tecp.pcloud.configure.center.core.exception.NoDataException;
import tecp.pcloud.configure.center.core.model.po.Role;
import tecp.pcloud.configure.center.core.model.vo.RoleInfo;
import tecp.pcloud.configure.center.core.model.vo.request.SearchPeametersRequest;
import tecp.pcloud.configure.center.core.model.vo.request.parameter.NameSearchParameters;
import tecp.pcloud.configure.center.core.model.vo.response.DataRowResponse;
import tecp.pcloud.configure.center.core.model.vo.response.DataRowsResponse;
import tecp.pcloud.configure.center.core.service.RoleManager;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName RoleManagerImpl
 * @Author pandong
 * @Date 2018/9/28 23:53
 **/
@Service
public class RoleManagerImpl implements RoleManager {
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public DataRowsResponse select(SearchPeametersRequest<NameSearchParameters> request) {
        int count = roleMapper.selectCount(request);
        List<DataRowResponse> roles = roleMapper.select(request).stream()
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
    public void insert(Role role) {
        roleMapper.insert(role);
    }

    @Override
    public void update(Role role) {
        roleMapper.update(role);
    }

    @Override
    public void delete(long id) {
        roleMapper.delete(id);
    }

    @Override
    public RoleInfo load(long id) {
        Role role = roleMapper.load(id);
        if(role==null){
            throw new NoDataException("data is not exist.");
        }
        RoleInfo roleInfo = new RoleInfo();
        roleInfo.setRole(role);
        roleInfo.setUsers(userMapper.selectByRole(id));
        return roleInfo;
    }
}
