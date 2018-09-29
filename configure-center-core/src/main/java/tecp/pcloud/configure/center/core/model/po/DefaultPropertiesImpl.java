package tecp.pcloud.configure.center.core.model.po;

import java.sql.Timestamp;

/**
 * @ClassName DefaultPropertiesImpl
 * @Author pandong
 * @Date 2018/9/28 13:33
 **/
public class DefaultPropertiesImpl implements DefaultProperties<Long> {
    private long id;
    private String name;
    private String status = Status.ACTIVE.name();
    private Timestamp createTime;
    private Timestamp updateTime;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Timestamp getCreateTime() {
        return createTime;
    }

    @Override
    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Override
    public Timestamp getUpdateTime() {
        return updateTime;
    }

    @Override
    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public Long getPK() {
        return getId();
    }

    @Override
    public void setPK(Long pk) {
        setId(pk);
    }

    @Override
    public String getStatus() {
        return status;
    }

    public Status getUserStatus() {
        return Status.valueOf(status);
    }

    public void setStatus(Status status) {
        this.status = status.name();
    }

    @Override
    public void setStatus(String status) {
        this.status = status;
    }
}
