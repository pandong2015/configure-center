package tecp.pcloud.configure.center.core.service;

import tecp.pcloud.configure.center.core.model.po.Configure;
import tecp.pcloud.configure.center.core.model.vo.ConfigureInfo;
import tecp.pcloud.configure.center.core.model.vo.request.SearchPeametersRequest;
import tecp.pcloud.configure.center.core.model.vo.request.parameter.ConfigureSearchParameters;
import tecp.pcloud.configure.center.core.model.vo.response.ConfiguresResponse;

/**
 * @ClassName ConfigureService
 * @Author pandong
 * @Date 2018/9/28 17:23
 **/
public interface ConfigureManager {
    void insert(final Configure configure);

    void update(final Configure configure);

    void delete(final Configure configure);

    ConfiguresResponse select(final SearchPeametersRequest<ConfigureSearchParameters> request);

    ConfigureInfo load(final long id);

    void copy(final long sourceServiceId, final long targetServiceId);

    void copy(final long sourceServiceId, final long targetServiceId, final long targetProfileId);

    void copy(final long sourceServiceId, final long sourceProfileId, final long targetServiceId, final long targetProfileId);
}
