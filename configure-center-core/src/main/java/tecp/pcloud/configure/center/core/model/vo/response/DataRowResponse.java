/**
 *
 */
package tecp.pcloud.configure.center.core.model.vo.response;

import java.sql.Timestamp;

/**
 * @author pandong
 * @ClassName ConfiguresResponse
 * @Date 2018年9月19日 下午10:55:13
 */
public class DataRowResponse {
    private long id;
    private String name;
    private String status;
    private Timestamp createTime;
    private Timestamp updateTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }
}
