package tecp.pcloud.configure.center.core.service;

import tecp.pcloud.configure.center.core.model.po.Profile;
import tecp.pcloud.configure.center.core.model.vo.ProfileInfo;
import tecp.pcloud.configure.center.core.model.vo.request.SearchPeametersRequest;
import tecp.pcloud.configure.center.core.model.vo.request.parameter.NameSearchParameters;
import tecp.pcloud.configure.center.core.model.vo.response.DataRowsResponse;
import tecp.pcloud.configure.center.core.model.vo.response.OptionResponse;

import java.util.List;

/**
 * @ClassName UserService
 * @Author pandong
 * @Date 2018/9/28 14:17
 **/
public interface ProfileManager {
    DataRowsResponse select(SearchPeametersRequest<NameSearchParameters> request);

    void insert(Profile profile);

    void update(Profile profile);

    void delete(long id);

    ProfileInfo load(long id);

    List<OptionResponse> allActive();
}
