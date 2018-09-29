package tech.pcloud.configure.center.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.pcloud.configure.center.server.config.AppProperties;
import tech.pcloud.configure.center.server.mapper.RoleMapper;
import tech.pcloud.configure.center.server.mapper.ServiceMapper;
import tech.pcloud.configure.center.server.mapper.TokenMapper;
import tech.pcloud.configure.center.server.mapper.UserMapper;
import tech.pcloud.configure.center.server.util.security.DESUtil;
import tecp.pcloud.configure.center.core.exception.LoginException;
import tecp.pcloud.configure.center.core.exception.NoDataException;
import tecp.pcloud.configure.center.core.exception.UnLoginOutException;
import tecp.pcloud.configure.center.core.model.po.Role;
import tecp.pcloud.configure.center.core.model.po.Token;
import tecp.pcloud.configure.center.core.model.po.User;
import tecp.pcloud.configure.center.core.model.vo.UserInfo;
import tecp.pcloud.configure.center.core.model.vo.request.LoginRequest;
import tecp.pcloud.configure.center.core.model.vo.request.SearchPeametersRequest;
import tecp.pcloud.configure.center.core.model.vo.request.parameter.UserSearchParameters;
import tecp.pcloud.configure.center.core.model.vo.response.UserResponse;
import tecp.pcloud.configure.center.core.model.vo.response.UsersResponse;
import tecp.pcloud.configure.center.core.service.Callback;
import tecp.pcloud.configure.center.core.service.UserManager;

import java.nio.charset.Charset;
import java.util.Base64;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @ClassName UserServiceImpl
 * @Author pandong
 * @Date 2018/9/28 15:22
 **/
@Service
public class UserManagerImpl implements UserManager {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private TokenMapper tokenMapper;
    @Autowired
    private AppProperties properties;
    @Autowired
    private ServiceMapper serviceMapper;

    @Override
    public String login(LoginRequest request, Callback<User> callback) {
        byte[] password = DESUtil.encrypt(request.getPassword(), properties.getSecurityKey());
        List<User> users = userMapper.checkUser(request.getUsername(), Base64.getEncoder().encodeToString(password));
        if (users.size() != 1) {
            throw new LoginException("Login fail, username or password is error.");
        }
        String token = UUID.randomUUID().toString();
        tokenMapper.save(token, users.get(0).getId());
        if (callback != null) {
            callback.call(users.get(0));
        }
        return token;
    }

    @Override
    public UserInfo info(String token) {
        Token tmp = tokenMapper.loadTokenByToken(token);
        if (tmp == null) {
            throw new UnLoginOutException("no login info.");
        }
        if (System.currentTimeMillis() - tmp.getUpdateTime().getTime() >= properties.getAccountTimeout()*60*1000) {
            String newToken = UUID.randomUUID().toString();
            tokenMapper.save(newToken, tmp.getUserId());
            tmp = tokenMapper.loadTokenByUserId(tmp.getUserId());
        }
        User user = userMapper.load(tmp.getUserId());
        List<Role> roles = roleMapper.selectByUserId(tmp.getUserId());
        UserInfo userInfo = new UserInfo();
        userInfo.setRoles(roles);
        userInfo.setToken(tmp);
        userInfo.setUser(user);
        return userInfo;
    }

    @Override
    public boolean logout(String token, Callback<User> callback) {
        Token tmp = tokenMapper.loadTokenByToken(token);
        User user = userMapper.load(tmp.getUserId());
        tokenMapper.delete(token);
        if (callback != null) {
            callback.call(user);
        }
        return true;
    }

    @Override
    public UsersResponse select(SearchPeametersRequest<UserSearchParameters> request) {
        int count = userMapper.selectCount(request);
        List<UserResponse> users = userMapper.select(request).stream()
                .map(u -> {
                    UserResponse userResponse = new UserResponse();
                    userResponse.setAccount(u.getAccount());
                    userResponse.setCreateTime(u.getCreateTime());
                    userResponse.setEmail(u.getEmail());
                    userResponse.setId(u.getId());
                    userResponse.setName(u.getName());
                    userResponse.setStatus(u.getStatus());
                    userResponse.setUpdateTime(u.getUpdateTime());
                    return userResponse;
                }).collect(Collectors.toList());
        UsersResponse usersResponse = new UsersResponse();
        usersResponse.setList(users);
        usersResponse.setRequest(request);
        usersResponse.setTotalSize(count);
        return usersResponse;
    }

    @Override
    public void insert(User user) {
        userMapper.insert(user);
    }

    @Override
    public void update(User user) {
        userMapper.update(user);
    }

    @Override
    public void delete(long id) {
        userMapper.delete(id);
    }

    @Override
    public UserInfo load(long id) {
        User user = userMapper.load(id);
        if(user==null){
            throw new NoDataException("data is not exist.");
        }
        UserInfo userInfo = new UserInfo();
        userInfo.setUser(user);
        userInfo.setRoles(roleMapper.selectByUserId(id));
        userInfo.setServices(serviceMapper.selectByUser(id));
        return userInfo;
    }
}
