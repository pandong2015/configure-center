package tech.pcloud.configure.center.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import tecp.pcloud.configure.center.core.model.po.Configure;
import tecp.pcloud.configure.center.core.model.rest.RestResult;
import tecp.pcloud.configure.center.core.model.vo.ConfigureInfo;
import tecp.pcloud.configure.center.core.model.vo.request.ConfigureCopyRequest;
import tecp.pcloud.configure.center.core.model.vo.request.SearchPeametersRequest;
import tecp.pcloud.configure.center.core.model.vo.request.parameter.ConfigureSearchParameters;
import tecp.pcloud.configure.center.core.model.vo.response.ConfiguresResponse;
import tecp.pcloud.configure.center.core.service.ConfigureManager;

/**
 * @ClassName ProfileController
 * @Author pandong
 * @Date 2018/9/28 22:40
 **/
@CrossOrigin("*")
@RestController
@RequestMapping("/data/configure")
public class ConfigureController extends AppController {
    @Autowired
    private ConfigureManager configureManager;

    @RequestMapping(value = "/{configureId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public RestResult<ConfigureInfo> load(final @PathVariable("configureId") long configureId) {
        return success(configureManager.load(configureId));
    }

    @RequestMapping(value = "/page", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public RestResult<ConfiguresResponse> page(final @RequestBody SearchPeametersRequest<ConfigureSearchParameters> request) {
        return success(configureManager.select(request));
    }

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public RestResult insert(final @RequestBody Configure configure) {
        configureManager.insert(configure);
        return success();
    }

    @RequestMapping(method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public RestResult update(final @RequestBody Configure configure) {
        configureManager.update(configure);
        return success();
    }

    @RequestMapping(method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public RestResult delete(final @RequestBody Configure configure) {
        configureManager.delete(configure);
        return success();
    }

    @RequestMapping(value = "/copy", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public RestResult copyToServiceAndProfile(final @RequestBody ConfigureCopyRequest request) {
        long sourceServiceId = request.getSourceServiceId();
        long sourceProfileId = request.getSourceProfileId();
        long targetServiceId = request.getTargetServiceId();
        long targetProfileId = request.getTargetProfileId();
        if (sourceServiceId > 0 && sourceProfileId > 0 && targetServiceId > 0 && targetProfileId > 0) {
            configureManager.copy(sourceServiceId, sourceProfileId, targetServiceId, targetProfileId);
        }
        if (sourceServiceId > 0 && sourceProfileId == 0 && targetServiceId > 0 && targetProfileId > 0) {
            configureManager.copy(sourceServiceId, targetServiceId, targetProfileId);
        }
        if (sourceServiceId > 0 && sourceProfileId == 0 && targetServiceId > 0 && targetProfileId == 0) {
            configureManager.copy(sourceServiceId, targetServiceId);
        }
        return success();
    }
}
