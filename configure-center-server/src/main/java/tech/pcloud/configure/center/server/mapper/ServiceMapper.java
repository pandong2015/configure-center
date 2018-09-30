package tech.pcloud.configure.center.server.mapper;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;
import org.apache.ibatis.mapping.StatementType;
import tecp.pcloud.configure.center.core.model.StatusProperty;
import tecp.pcloud.configure.center.core.model.po.Service;
import tecp.pcloud.configure.center.core.model.vo.request.SearchPeametersRequest;
import tecp.pcloud.configure.center.core.model.vo.request.parameter.ServiceSearchParameters;

import java.sql.Timestamp;
import java.util.List;

public interface ServiceMapper {
    @Insert("insert into service(name, user_id, private_key, public_key) values(#{service.name}, #{service.userId}, #{service.privateKey}, #{service.publicKey})")
    @SelectKey(before = false, keyColumn = "id", keyProperty = "service.id", resultType = Long.class,
            statementType = StatementType.STATEMENT, statement = "SELECT LAST_INSERT_ID() AS id")
    void save(final @Param("service") Service service);

    @Update("update service set name=#{service.name} where id=#{service.id}")
    void updateServiceName(final @Param("service") Service service);

    @Update("update service set private_key=#{service.privteKey},public_key=#{service.publicKey}  where id=#{service.id}")
    void updateServiceKey(final @Param("service") Service service);

    @Update("update service set status=#{status} where id=#{id}")
    void updateStatus(@Param("id") long id, @Param("status") String status);

    default void delete(long id) {
        updateStatus(id, StatusProperty.Status.DELETE.name());
    }

    default void active(long id) {
        updateStatus(id, StatusProperty.Status.ACTIVE.name());
    }

    @Results(id = "ServiceResultMap", value = {
            @Result(column = "user_id", property = "userId", javaType = Long.class),
            @Result(column = "public_key", property = "publicKey", javaType = String.class),
            @Result(column = "private_key", property = "privateKey", javaType = String.class),
            @Result(column = "create_time", property = "createTime", javaType = Timestamp.class),
            @Result(column = "update_time", property = "updateTime", javaType = Timestamp.class)
    })
    @Select("select id, name, user_id, private_key, public_key, status, create_time, update_time from service where id=#{id}")
    Service load(final @Param("id") long id);

    @Select("select id, name, user_id, private_key, public_key, status, create_time, update_time from service where name=#{name}")
    Service loadByName(final @Param("name") String name);

    @ResultMap("ServiceResultMap")
    @SelectProvider(type = ServiceProvider.class, method = "select")
    List<Service> select(final @Param("request") SearchPeametersRequest<ServiceSearchParameters> request);

    @SelectProvider(type = ServiceProvider.class, method = "selectCount")
    int selectCount(final @Param("request") SearchPeametersRequest<ServiceSearchParameters> request);

    @ResultMap("ServiceResultMap")
    @Select("select id, name, user_id, private_key, public_key, status, create_time, update_time from service where user_id=#{userId} and status='ACTIVE'")
    List<Service> selectByUser(final @Param("userId") long userId);

    @ResultMap("ServiceResultMap")
    @Select("select id, name, user_id, private_key, public_key, status, create_time, update_time from service where status='ACTIVE'")
    List<Service> allActive();

    class ServiceProvider extends PageProvider<ServiceSearchParameters> {
        @Override
        public String getTable() {
            return "service";
        }

        @Override
        public String getCloumns() {
            return "id, name, user_id, private_key, public_key, status, create_time, update_time";
        }

        @Override
        public void doWhere(SQL sql, ServiceSearchParameters parameters) {
            if (StringUtils.isNotBlank(parameters.getName())) {
                sql.AND().WHERE("name like concat(concat('%', #{request.parameters.name}),'%')");
            }
            if (StringUtils.isBlank(parameters.getStatus())) {
                parameters.setStatus("ACTIVE");
            }
            if (StringUtils.isNotBlank(parameters.getStatus())) {
                sql.AND().WHERE("status = #{request.parameters.status}");
            }
            if (parameters.getUserId() > 0) {
                sql.AND().WHERE("user_id = #{request.parameters.userId}");
            }
        }
    }
}
