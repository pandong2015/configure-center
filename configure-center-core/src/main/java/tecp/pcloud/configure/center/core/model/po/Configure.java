package tecp.pcloud.configure.center.core.model.po;

/**
 * @ClassName Configure
 * @Author pandong
 * @Date 2018/9/28 13:44
 **/
public class Configure extends DefaultPropertiesImpl {
    public enum ConfigureType {
        AUTO, RESTART
    }

    private String value;
    private long profileId;
    private long serviceId;
    private String type = ConfigureType.AUTO.name();

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public long getProfileId() {
        return profileId;
    }

    public void setProfileId(long profileId) {
        this.profileId = profileId;
    }

    public long getServiceId() {
        return serviceId;
    }

    public void setServiceId(long serviceId) {
        this.serviceId = serviceId;
    }

    public String getType() {
        return type;
    }

    public ConfigureType getConfigureType() {
        return ConfigureType.valueOf(type);
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setType(ConfigureType type) {
        this.type = type.name();
    }
}
