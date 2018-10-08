package tecp.pcloud.configure.center.core.service;

import tecp.pcloud.configure.center.core.model.po.User;
import tecp.pcloud.configure.center.core.model.vo.UserInfo;
import tecp.pcloud.configure.center.core.model.vo.request.LoginRequest;
import tecp.pcloud.configure.center.core.model.vo.request.SearchPeametersRequest;
import tecp.pcloud.configure.center.core.model.vo.request.parameter.UserSearchParameters;
import tecp.pcloud.configure.center.core.model.vo.response.UsersResponse;

/**
 * @ClassName UserService
 * @Author pandong
 * @Date 2018/9/28 14:17
 **/
public interface UserManager {
    String login(final LoginRequest request, final Callback<User> callback);

    UserInfo info(final String token);

    boolean logout(final String token, final Callback<User> callback);

    UsersResponse select(SearchPeametersRequest<UserSearchParameters> request);

    void insert(User user);

    void update(User user);

    void updatePassword(User user);

    void updatePassword(long userId);

    default void updatePassword(long id, String password) {
        User user = new User();
        user.setPassword(password);
        user.setId(id);
        updatePassword(user);
    }

    void delete(long id);

    UserInfo load(long id);
}
