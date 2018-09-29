package tecp.pcloud.configure.center.core.model;

import java.sql.Timestamp;

/**
 * @ClassName IUpdateTimeProperty
 * @Author pandong
 * @Date 2018/9/28 13:25
 **/
public interface UpdateTimeProperty {
    Timestamp getUpdateTime();
    void setUpdateTime(Timestamp updateTime);
}
