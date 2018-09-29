/**
 *
 */
package tecp.pcloud.configure.center.core.model.vo.response;

import tecp.pcloud.configure.center.core.model.po.Profile;
import tecp.pcloud.configure.center.core.model.po.Service;

import java.sql.Timestamp;

/**
 * @author pandong
 * @ClassName ConfiguresResponse
 * @Date 2018年9月19日 下午10:55:13
 */
public class ConfigureResponse {
    private String name;
    private String value;
    private String status;
    private String type;
    private long serviceId;
    private long profileId;
    private String profileName;
    private String serviceName;
    private Timestamp createTime;
    private Timestamp updateTime;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getServiceId() {
        return serviceId;
    }

    public void setServiceId(long serviceId) {
        this.serviceId = serviceId;
    }

    public long getProfileId() {
        return profileId;
    }

    public void setProfileId(long profileId) {
        this.profileId = profileId;
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

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
}
