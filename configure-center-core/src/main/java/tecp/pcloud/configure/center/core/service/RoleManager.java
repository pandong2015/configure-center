package tecp.pcloud.configure.center.core.service;

import tecp.pcloud.configure.center.core.model.po.Role;
import tecp.pcloud.configure.center.core.model.po.User;
import tecp.pcloud.configure.center.core.model.vo.RoleInfo;
import tecp.pcloud.configure.center.core.model.vo.UserInfo;
import tecp.pcloud.configure.center.core.model.vo.request.LoginRequest;
import tecp.pcloud.configure.center.core.model.vo.request.SearchPeametersRequest;
import tecp.pcloud.configure.center.core.model.vo.request.parameter.NameSearchParameters;
import tecp.pcloud.configure.center.core.model.vo.request.parameter.UserSearchParameters;
import tecp.pcloud.configure.center.core.model.vo.response.DataRowsResponse;
import tecp.pcloud.configure.center.core.model.vo.response.UsersResponse;

/**
 * @ClassName UserService
 * @Author pandong
 * @Date 2018/9/28 14:17
 **/
public interface RoleManager {
    DataRowsResponse select(SearchPeametersRequest<NameSearchParameters> request);

    void insert(Role role);

    void update(Role role);

    void delete(long id);

    RoleInfo load(long id);
}
