package tech.pcloud.configure.center.server.mapper;

import org.apache.ibatis.annotations.*;
import tecp.pcloud.configure.center.core.model.po.Token;

import java.sql.Timestamp;

public interface TokenMapper {
    @Insert("insert into token(token, user_id) values(#{token.token}, #{token.userId}) on duplicate key update token=#{token.token}")
    void save(final @Param("token") Token token);

    default void save(final String token, final long userId) {
        Token tmp = new Token();
        tmp.setUserId(userId);
        tmp.setToken(token);
        save(tmp);
    }

    @Select("select token, user_id, create_time, update_time from token where user_id=#{userId}")
    @Results(value = {
            @Result(column = "user_id", property = "userId", javaType = Long.class),
            @Result(column = "create_time", property = "createTime", javaType = Timestamp.class),
            @Result(column = "update_time", property = "updateTime", javaType = Timestamp.class)
    })
    Token loadTokenByUserId(final @Param("userId") long userId);

    @Select("select token, user_id, create_time, update_time from token where token=#{token}")
    @Results(value = {
            @Result(column = "user_id", property = "userId", javaType = Long.class),
            @Result(column = "create_time", property = "createTime", javaType = Timestamp.class),
            @Result(column = "update_time", property = "updateTime", javaType = Timestamp.class)
    })
    Token loadTokenByToken(final @Param("token") String token);

    @Delete("delete from token where token=#{token}")
    void delete(final @Param("token") String token);
}
