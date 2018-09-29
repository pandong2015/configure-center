package tech.pcloud.configure.center.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import tecp.pcloud.configure.center.core.model.po.Profile;
import tecp.pcloud.configure.center.core.model.po.Role;
import tecp.pcloud.configure.center.core.model.rest.RestResult;
import tecp.pcloud.configure.center.core.model.vo.ProfileInfo;
import tecp.pcloud.configure.center.core.model.vo.RoleInfo;
import tecp.pcloud.configure.center.core.model.vo.request.SearchPeametersRequest;
import tecp.pcloud.configure.center.core.model.vo.request.parameter.NameSearchParameters;
import tecp.pcloud.configure.center.core.model.vo.response.DataRowsResponse;
import tecp.pcloud.configure.center.core.model.vo.response.OptionResponse;
import tecp.pcloud.configure.center.core.service.ProfileManager;
import tecp.pcloud.configure.center.core.service.RoleManager;

import java.util.List;

/**
 * @ClassName ProfileController
 * @Author pandong
 * @Date 2018/9/28 22:40
 **/
@CrossOrigin("*")
@RestController
@RequestMapping("/data/profile")
public class ProfileController extends AppController {
    @Autowired
    private ProfileManager profileManager;

    @RequestMapping(value = "/{profileId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public RestResult<ProfileInfo> load(final @PathVariable("profileId") long profileId) {
        return success(profileManager.load(profileId));
    }

    @RequestMapping(value = "/page", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public RestResult<DataRowsResponse> page(final @RequestBody SearchPeametersRequest<NameSearchParameters> request) {
        return success(profileManager.select(request));
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public RestResult<List<OptionResponse>> all() {
        return success(profileManager.allActive());
    }

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public RestResult insert(final @RequestBody Profile profile) {
        profileManager.insert(profile);
        return success();
    }

    @RequestMapping(method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public RestResult update(final @RequestBody Profile profile) {
        profileManager.update(profile);
        return success();
    }

    @RequestMapping(value = "/{profileId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public RestResult delete(final @PathVariable("profileId") long profileId) {
        profileManager.delete(profileId);
        return success();
    }
}
