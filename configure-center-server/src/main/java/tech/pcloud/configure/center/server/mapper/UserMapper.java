package tech.pcloud.configure.center.server.mapper;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;
import org.apache.ibatis.mapping.StatementType;
import tecp.pcloud.configure.center.core.model.vo.request.parameter.UserSearchParameters;
import tecp.pcloud.configure.center.core.model.StatusProperty;
import tecp.pcloud.configure.center.core.model.po.User;
import tecp.pcloud.configure.center.core.model.vo.request.SearchPeametersRequest;

import java.sql.Timestamp;
import java.util.List;

/**
 * @ClassName UserMapper
 * @Author pandong
 * @Date 2018/9/28 15:23
 **/
public interface UserMapper {
    @Insert("insert into user(name, email, account, password) values(#{user.name}, #{user.email}, #{user.account}, #{user.password})")
    @SelectKey(before = false, keyColumn = "id", keyProperty = "user.id", resultType = Long.class,
            statementType = StatementType.STATEMENT, statement = "SELECT LAST_INSERT_ID() AS id")
    long insert(@Param("user") User user);

    @Update("update user set name=#{user.name}, email=#{user.email} where id=#{user.id}")
    void update(@Param("user") User user);

    @Update("update user set password=#{user.password} where id=#{user.id}")
    void updatePassword(@Param("user") User user);

    @Update("update user set status=#{status} where id=#{id}")
    void updateStatus(@Param("id") long id, @Param("status") String status);

    default void delete(long id) {
        updateStatus(id, StatusProperty.Status.DELETE.name());
    }

    default void active(long id) {
        updateStatus(id, StatusProperty.Status.ACTIVE.name());
    }

    @SelectProvider(type = UserProvider.class, method = "select")
    @Results(id = "UserResultMap", value = {
            @Result(column = "create_time", property = "createTime", javaType = Timestamp.class),
            @Result(column = "update_time", property = "updateTime", javaType = Timestamp.class)
    })
    List<User> select(@Param("request") final SearchPeametersRequest<UserSearchParameters> request);

    @SelectProvider(type = UserProvider.class, method = "selectCount")
    int selectCount(@Param("request") final SearchPeametersRequest<UserSearchParameters> request);

    @ResultMap("UserResultMap")
    @Select("select id, name, email, account, status, create_time, update_time from user where status='ACTIVE' and account=#{account} and password=#{password}")
    List<User> checkUser(final @Param("account") String account, final @Param("password") String password);

    @ResultMap("UserResultMap")
    @Select("select id, name, email, account, status, create_time, update_time from user where id=#{id}")
    User load(final @Param("id") long id);

    @ResultMap("UserResultMap")
    @Select("select a.id, a.name, a.email, a.account, a.status, a.create_time, a.update_time from user a left join user_role_ref b on a.id=b.user_id where a.status='ACTIVE' and b.role_id=#{roleId} ")
    List<User> selectByRole(final @Param("roleId") long roleId);

    class UserProvider extends PageProvider<UserSearchParameters> {
        @Override
        public String getTable() {
            return "user";
        }

        @Override
        public String getCloumns() {
            return "id, name, email, account, status, create_time, update_time";
        }

        @Override
        public void doWhere(SQL sql, UserSearchParameters parameters) {
            if (StringUtils.isNotBlank(parameters.getName())) {
                sql.AND().WHERE("name like concat(concat('%', #{request.parameters.name}),'%')");
            }
            if (StringUtils.isNotBlank(parameters.getAccount())) {
                sql.AND().WHERE("account like concat(concat('%', #{request.parameters.account}),'%')");
            }
            if (StringUtils.isNotBlank(parameters.getEmail())) {
                sql.AND().WHERE("email like concat(concat('%', #{request.parameters.email}),'%')");
            }
            if (StringUtils.isBlank(parameters.getStatus())) {
                parameters.setStatus("ACTIVE");
            }
            if (StringUtils.isNotBlank(parameters.getStatus())) {
                sql.AND().WHERE("status = #{request.parameters.status}");
            }
        }
    }
}
