package tech.pcloud.configure.center.server.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;
import tecp.pcloud.configure.center.core.model.SearchParameters;
import tecp.pcloud.configure.center.core.model.vo.request.SearchPeametersRequest;

/**
 * @ClassName PageProvider
 * @Author pandong
 * @Date 2018/9/28 21:54
 **/
public abstract class PageProvider<T extends SearchParameters> {
    public abstract String getTable();
    public abstract String getCloumns();

    public String select(@Param("request") final SearchPeametersRequest<T> request) {
        SQL sql = new SQL();
        sql.SELECT(getCloumns());
        sql.FROM(getTable());
        where(sql, request.getParameters());
        return BasicProvider.page(sql, request.getPage());
    }

    public String selectCount(@Param("request") final SearchPeametersRequest<T> request) {
        SQL sql = new SQL();
        sql.SELECT("count(0)");
        sql.FROM(getTable());
        where(sql, request.getParameters());
        return sql.toString();
    }

    private void where(SQL sql, T parameters) {
        if (parameters == null) {
            return;
        }
        sql.WHERE("1=1");
        doWhere(sql, parameters);
    }

    public abstract void doWhere(SQL sql, T parameters);
}
