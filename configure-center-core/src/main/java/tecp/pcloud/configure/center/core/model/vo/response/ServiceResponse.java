/**
 * 
 */
package tecp.pcloud.configure.center.core.model.vo.response;

import tecp.pcloud.configure.center.core.model.po.User;

import java.sql.Timestamp;

/**
 * @ClassName ConfiguresResponse
 * @author pandong
 * @Date 2018年9月19日 下午10:55:13
 */
public class ServiceResponse {
	private long id;
	private String name;
	private String status;
	private long userId;
	private String userName;
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

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
