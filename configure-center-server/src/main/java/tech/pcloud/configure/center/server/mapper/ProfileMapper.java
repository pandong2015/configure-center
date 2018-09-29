package tech.pcloud.configure.center.server.mapper;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;
import org.apache.ibatis.mapping.StatementType;
import tecp.pcloud.configure.center.core.model.StatusProperty;
import tecp.pcloud.configure.center.core.model.po.Profile;
import tecp.pcloud.configure.center.core.model.po.Role;
import tecp.pcloud.configure.center.core.model.vo.request.SearchPeametersRequest;
import tecp.pcloud.configure.center.core.model.vo.request.parameter.NameSearchParameters;

import java.sql.Timestamp;
import java.util.List;

/**
 * @ClassName RoleMapper
 * @Author pandong
 * @Date 2018/9/28 15:23
 **/
public interface ProfileMapper {
    @Insert("insert into profile(name) value (#{profile.name})")
    @SelectKey(before = false, keyColumn = "id", keyProperty = "profile.id", resultType = Long.class,
            statementType = StatementType.STATEMENT, statement = "SELECT LAST_INSERT_ID() AS id")
    long insert(@Param("profile") Profile profile);

    @Update("update profile set name=#{profile.name} where id=#{profile.id}")
    void update(@Param("profile") Profile profile);

    @Update("update profile set status=#{status} where id=#{id}")
    void updateStatus(@Param("id") long id, @Param("status") String status);

    default void delete(long id) {
        updateStatus(id, StatusProperty.Status.DELETE.name());
    }

    default void active(long id) {
        updateStatus(id, StatusProperty.Status.ACTIVE.name());
    }

    @SelectProvider(type = ProfileProvider.class, method = "select")
    @Results(id = "ProfileResultMap", value = {
            @Result(column = "create_time", property = "createTime", javaType = Timestamp.class),
            @Result(column = "update_time", property = "updateTime", javaType = Timestamp.class)
    })
    List<Profile> select(@Param("request") final SearchPeametersRequest<NameSearchParameters> request);

    @SelectProvider(type = ProfileProvider.class, method = "selectCount")
    int selectCount(@Param("request") final SearchPeametersRequest<NameSearchParameters> request);

    @ResultMap("ProfileResultMap")
    @Select("select a.id, a.name, a.status, a.create_time, a.update_time from profile a where a.id=#{id}")
    Profile load(final @Param("id") long id);

    @ResultMap("ProfileResultMap")
    @Select("select a.id, a.name, a.status, a.create_time, a.update_time from profile a where a.status='ACTIVE'")
    List<Profile> allActive();

    class ProfileProvider extends PageProvider<NameSearchParameters> {
        @Override
        public String getTable() {
            return "profile";
        }

        @Override
        public String getCloumns() {
            return "id, name, status, create_time, update_time";
        }

        @Override
        public void doWhere(SQL sql, NameSearchParameters parameters) {
            if (StringUtils.isNotBlank(parameters.getName())) {
                sql.AND().WHERE("name like concat(concat('%', #{request.parameters.name}),'%')");
            }
            if (StringUtils.isNotBlank(parameters.getStatus())) {
                sql.AND().WHERE("status = #{request.parameters.status}");
            }
        }
    }
}
