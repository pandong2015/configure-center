package tech.pcloud.configure.center.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import tecp.pcloud.configure.center.core.model.po.Role;
import tecp.pcloud.configure.center.core.model.po.User;
import tecp.pcloud.configure.center.core.model.rest.RestResult;
import tecp.pcloud.configure.center.core.model.vo.RoleInfo;
import tecp.pcloud.configure.center.core.model.vo.UserInfo;
import tecp.pcloud.configure.center.core.model.vo.request.SearchPeametersRequest;
import tecp.pcloud.configure.center.core.model.vo.request.parameter.NameSearchParameters;
import tecp.pcloud.configure.center.core.model.vo.request.parameter.UserSearchParameters;
import tecp.pcloud.configure.center.core.model.vo.response.DataRowsResponse;
import tecp.pcloud.configure.center.core.model.vo.response.UsersResponse;
import tecp.pcloud.configure.center.core.service.RoleManager;
import tecp.pcloud.configure.center.core.service.UserManager;

/**
 * @ClassName RoleController
 * @Author pandong
 * @Date 2018/9/28 22:40
 **/
@CrossOrigin("*")
@RestController
@RequestMapping("/data/role")
public class RoleController extends AppController {
    @Autowired
    private RoleManager roleManager;

    @RequestMapping(value = "/{roleId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public RestResult<RoleInfo> load(final @PathVariable("roleId") long roleId) {
        return success(roleManager.load(roleId));
    }

    @RequestMapping(value = "/page", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public RestResult<DataRowsResponse> page(final @RequestBody SearchPeametersRequest<NameSearchParameters> request) {
        return success(roleManager.select(request));
    }

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public RestResult insert(final @RequestBody Role role) {
        roleManager.insert(role);
        return success();
    }

    @RequestMapping(method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public RestResult update(final @RequestBody Role role) {
        roleManager.update(role);
        return success();
    }

    @RequestMapping(value = "/{roleId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public RestResult delete(final @PathVariable("roleId") long roleId) {
        roleManager.delete(roleId);
        return success();
    }
}
