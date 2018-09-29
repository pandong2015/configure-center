package tech.pcloud.configure.center.server.mapper;

import org.apache.ibatis.jdbc.SQL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tecp.pcloud.configure.center.core.model.PageInfo;

/**
 * @ClassName BasicProvider
 * @Author pandong
 * @Date 2018/9/28 15:48
 **/
public class BasicProvider {
    private static final Logger log = LoggerFactory.getLogger(BasicProvider.class);

    public static String page(SQL sql, PageInfo pageInfo) {
        StringBuffer sqlStr = new StringBuffer(sql.toString());
        if (pageInfo != null) {
            sqlStr.append(" LIMIT #{request.page.start}, #{request.page.pageSize} ");
        }
        log.debug("page select sql: " + sqlStr.toString());
        return sqlStr.toString();
    }
}
