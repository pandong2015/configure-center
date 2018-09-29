package tech.pcloud.configure.center.server.mapper;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;
import tecp.pcloud.configure.center.core.model.StatusProperty;
import tecp.pcloud.configure.center.core.model.po.Configure;
import tecp.pcloud.configure.center.core.model.vo.request.SearchPeametersRequest;
import tecp.pcloud.configure.center.core.model.vo.request.parameter.ConfigureSearchParameters;
import tecp.pcloud.configure.center.core.model.vo.request.parameter.NameSearchParameters;

import java.sql.Timestamp;
import java.util.List;

/**
 * @ClassName RoleMapper
 * @Author pandong
 * @Date 2018/9/28 15:23
 **/
public interface ConfigureMapper {
    @Insert("insert into configure(name, value, profile_id, service_id) value (#{configure.name}, #{configure.value}, #{configure.profileId}, #{configure.serviceId})")
    long insert(@Param("configure") Configure configure);

    @Update("update configure set value=#{configure.value}, type=#{configure.type} where name=#{configure.name} and profile_id=#{configure.profileId} and service_id=#{configure.serviceId}")
    void update(@Param("configure") Configure configure);

    @Update("update configure set status=#{status} where name=#{configure.name} and profile_id=#{configure.profileId} and service_id=#{configure.serviceId}")
    void updateStatus(@Param("configure") Configure configure, @Param("status") String status);

    default void delete(Configure configure) {
        updateStatus(configure, StatusProperty.Status.DELETE.name());
    }

    default void active(Configure configure) {
        updateStatus(configure, StatusProperty.Status.ACTIVE.name());
    }

    @SelectProvider(type = ConfigureProvider.class, method = "select")
    @Results(id = "ConfigureResultMap", value = {
            @Result(column = "profile_id", property = "profileId", javaType = Long.class),
            @Result(column = "service_id", property = "serviceId", javaType = Long.class),
            @Result(column = "create_time", property = "createTime", javaType = Timestamp.class),
            @Result(column = "update_time", property = "updateTime", javaType = Timestamp.class)
    })
    List<Configure> select(@Param("request") final SearchPeametersRequest<ConfigureSearchParameters> request);

    @SelectProvider(type = ConfigureProvider.class, method = "selectCount")
    int selectCount(@Param("request") final SearchPeametersRequest<ConfigureSearchParameters> request);

    @ResultMap("ConfigureResultMap")
    @Select("select name, status, create_time, update_time, value, profile_id, service_id, type from role a where a.id=#{id}")
    Configure load(final @Param("id") long id);

    @Insert("insert into configure(name, value, profile_id, service_id) (select name, value, profile_id, ${targetId} from configure where service_id=#{sourceId})")
    void copyServiceConfig(final @Param("sourceId") long sourceId, final @Param("targetId") long targetId);

    @Insert("insert into configure(name, value, profile_id, service_id) (select name, value, ${targetProfileId}, ${targetId} from configure where service_id=#{sourceId})")
    void copyServiceConfigToProfile(final @Param("sourceId") long sourceId, final @Param("targetId") long targetId,
                                    final @Param("targetProfileId") long targetProfileId);

    @Insert("insert into configure(name, value, profile_id, service_id) (select name, value, ${targetProfileId}, ${targetServiceId} from configure where service_id=#{sourceServiceId} and profile_id=#{sourceProfuleId})")
    void copyServiceAndProfileConfig(final @Param("sourceServiceId") long sourceServiceId, final @Param("sourceProfileId") long sourceProfuleId,
                                     final @Param("targetServiceId") long targetServiceId, final @Param("targetProfileId") long targetProfileId);

    class ConfigureProvider extends PageProvider<ConfigureSearchParameters> {
        @Override
        public String getTable() {
            return "configure";
        }

        @Override
        public String getCloumns() {
            return "name, status, create_time, update_time, value, profile_id, service_id, type";
        }

        @Override
        public void doWhere(SQL sql, ConfigureSearchParameters parameters) {
            if (StringUtils.isNotBlank(parameters.getName())) {
                sql.AND().WHERE("name like concat(concat('%', #{request.parameters.name}),'%')");
            }
            if (StringUtils.isNotBlank(parameters.getStatus())) {
                sql.AND().WHERE("status = #{request.parameters.status}");
            }
            if (StringUtils.isNotBlank(parameters.getType())) {
                sql.AND().WHERE("type = #{request.parameters.type}");
            }
            if (!(parameters.getServiceIds() == null || parameters.getServiceIds().isEmpty())) {
                sql.AND();
                StringBuilder serviceIds = new StringBuilder();
                for (int i = 0; i < parameters.getServiceIds().size(); i++) {
                    serviceIds.append("#{request.parameters.serviceIds[").append(i).append("]},");
                }
                sql.WHERE("service_id in (" + serviceIds.deleteCharAt(serviceIds.length() - 1) + ")");
            }
            if (!(parameters.getProfileIds() == null || parameters.getProfileIds().isEmpty())) {
                sql.AND();
                StringBuilder profileIds = new StringBuilder();
                for (int i = 0; i < parameters.getProfileIds().size(); i++) {
                    profileIds.append("#{request.parameters.profileIds[").append(i).append("]},");
                }
                sql.WHERE("profile_id in (" + profileIds.deleteCharAt(profileIds.length() - 1) + ")");
            }
        }
    }
}
