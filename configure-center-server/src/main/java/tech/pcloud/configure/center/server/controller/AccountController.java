package tech.pcloud.configure.center.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import tecp.pcloud.configure.center.core.model.po.User;
import tecp.pcloud.configure.center.core.model.rest.RestResult;
import tecp.pcloud.configure.center.core.model.vo.UserInfo;
import tecp.pcloud.configure.center.core.model.vo.request.LoginRequest;
import tecp.pcloud.configure.center.core.model.vo.response.LoginResponse;
import tecp.pcloud.configure.center.core.service.Callback;
import tecp.pcloud.configure.center.core.service.UserManager;

/**
 * @ClassName AccountController
 * @Author pandong
 * @Date 2018/9/28 22:40
 **/
@CrossOrigin("*")
@RestController
@RequestMapping("/account")
public class AccountController extends AppController {
    public static final String SESSION_ATTRIBUTE_USER = "USER";

    @Autowired
    private UserManager userManager;

    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public RestResult<LoginResponse> login(@RequestBody LoginRequest request) {
        String token = userManager.login(request, new Callback<User>() {
            @Override
            public void call(User user) {
                addSessionAttribute(SESSION_ATTRIBUTE_USER, user);
            }
        });
        LoginResponse response = new LoginResponse();
        response.setToken(token);
        return success(response);
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public RestResult logout(@RequestParam("token") String token) {
        userManager.logout(token, new Callback<User>() {
            @Override
            public void call(User user) {
                log.info(user.getName() + " logout.");
                removeSessionAttribute(SESSION_ATTRIBUTE_USER);
            }
        });
        return success();
    }

    @RequestMapping(value = "/info", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public RestResult<UserInfo> info(@RequestParam("token") String token) {
        return success(userManager.info(token));
    }

}
