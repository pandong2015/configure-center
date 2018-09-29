package tech.pcloud.configure.center.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import tecp.pcloud.configure.center.core.model.po.Profile;
import tecp.pcloud.configure.center.core.model.po.Service;
import tecp.pcloud.configure.center.core.model.rest.RestResult;
import tecp.pcloud.configure.center.core.model.vo.ProfileInfo;
import tecp.pcloud.configure.center.core.model.vo.ServiceInfo;
import tecp.pcloud.configure.center.core.model.vo.request.SearchPeametersRequest;
import tecp.pcloud.configure.center.core.model.vo.request.parameter.NameSearchParameters;
import tecp.pcloud.configure.center.core.model.vo.request.parameter.ServiceSearchParameters;
import tecp.pcloud.configure.center.core.model.vo.response.DataRowsResponse;
import tecp.pcloud.configure.center.core.model.vo.response.OptionResponse;
import tecp.pcloud.configure.center.core.model.vo.response.ServicesResponse;
import tecp.pcloud.configure.center.core.service.ProfileManager;
import tecp.pcloud.configure.center.core.service.ServiceManager;

import java.util.List;

/**
 * @ClassName ServiceController
 * @Author pandong
 * @Date 2018/9/28 22:40
 **/
@CrossOrigin("*")
@RestController
@RequestMapping("/data/service")
public class ServiceController extends AppController {
    @Autowired
    private ServiceManager serviceManager;

    @RequestMapping(value = "/{serviceId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public RestResult<ServiceInfo> load(final @PathVariable("serviceId") long serviceId) {
        return success(serviceManager.load(serviceId));
    }

    @RequestMapping(value = "/page", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public RestResult<ServicesResponse> page(final @RequestBody SearchPeametersRequest<ServiceSearchParameters> request) {
        return success(serviceManager.select(request));
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public RestResult<List<OptionResponse>> all() {
        return success(serviceManager.allActive());
    }

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public RestResult insert(final @RequestBody Service service) {
        serviceManager.insert(service);
        return success();
    }

    @RequestMapping(method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public RestResult update(final @RequestBody Service service) {
        serviceManager.update(service);
        return success();
    }

    @RequestMapping(value = "/{serviceId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public RestResult delete(final @PathVariable("serviceId") long serviceId) {
        serviceManager.delete(serviceId);
        return success();
    }
}
