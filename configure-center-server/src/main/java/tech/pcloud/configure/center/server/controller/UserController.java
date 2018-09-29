package tech.pcloud.configure.center.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import tecp.pcloud.configure.center.core.model.po.User;
import tecp.pcloud.configure.center.core.model.rest.RestResult;
import tecp.pcloud.configure.center.core.model.vo.UserInfo;
import tecp.pcloud.configure.center.core.model.vo.request.SearchPeametersRequest;
import tecp.pcloud.configure.center.core.model.vo.request.parameter.UserSearchParameters;
import tecp.pcloud.configure.center.core.model.vo.response.UsersResponse;
import tecp.pcloud.configure.center.core.service.UserManager;

/**
 * @ClassName UserController
 * @Author pandong
 * @Date 2018/9/28 22:40
 **/
@CrossOrigin("*")
@RestController
@RequestMapping("/data/user")
public class UserController extends AppController {
    @Autowired
    private UserManager userManager;

    @RequestMapping(value = "/{userId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public RestResult<UserInfo> load(final @PathVariable("userId") long userId) {
        return success(userManager.load(userId));
    }

    @RequestMapping(value = "/page", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public RestResult<UsersResponse> page(final @RequestBody SearchPeametersRequest<UserSearchParameters> request) {
        return success(userManager.select(request));
    }

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public RestResult insert(final @RequestBody User user) {
        userManager.insert(user);
        return success();
    }

    @RequestMapping(method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public RestResult update(final @RequestBody User user) {
        userManager.update(user);
        return success();
    }

    @RequestMapping(method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public RestResult delete(final @PathVariable("userId") long userId) {
        userManager.delete(userId);
        return success();
    }
}
