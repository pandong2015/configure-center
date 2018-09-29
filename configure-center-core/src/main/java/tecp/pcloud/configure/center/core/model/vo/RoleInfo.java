package tecp.pcloud.configure.center.core.model.vo;

import com.google.common.collect.Lists;
import tecp.pcloud.configure.center.core.model.po.Role;
import tecp.pcloud.configure.center.core.model.po.User;

import java.util.List;

/**
 * @ClassName ROleInfo
 * @Author pandong
 * @Date 2018/9/28 13:52
 **/
public class RoleInfo {
    private Role role;
    private List<User> users = Lists.newArrayList();

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public User getUser(int index){
        return users.get(index);
    }

    public List<User> getUsers() {
        return users;
    }

    public void addUser(User user){
        users.add(user);
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public int getUserSize(){
        return users.size();
    }
}
