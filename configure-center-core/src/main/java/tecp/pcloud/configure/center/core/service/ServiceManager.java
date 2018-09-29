package tecp.pcloud.configure.center.core.service;

import tecp.pcloud.configure.center.core.model.po.Service;
import tecp.pcloud.configure.center.core.model.vo.ServiceInfo;
import tecp.pcloud.configure.center.core.model.vo.request.SearchPeametersRequest;
import tecp.pcloud.configure.center.core.model.vo.request.parameter.ServiceSearchParameters;
import tecp.pcloud.configure.center.core.model.vo.response.OptionResponse;
import tecp.pcloud.configure.center.core.model.vo.response.ServicesResponse;

import java.util.List;

/**
 * @ClassName ServiceManager
 * @Author pandong
 * @Date 2018/9/28 17:37
 **/
public interface ServiceManager {
    void insert(final Service service);

    void update(final Service service);

    void updateKey(final Service service);

    void delete(final long id);

    ServicesResponse select(final SearchPeametersRequest<ServiceSearchParameters> request);

    ServiceInfo load(final long id);

    List<OptionResponse> allActive();
}
