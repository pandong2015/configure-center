package tecp.pcloud.configure.center.core.model.vo.request;

/**
 * @ClassName ConfigureCopyRequest
 * @Author pandong
 * @Date 2018/9/29 23:41
 **/
public class ConfigureCopyRequest {
    private long sourceServiceId;
    private long sourceProfileId;
    private long targetServiceId;
    private long targetProfileId;

    public long getSourceServiceId() {
        return sourceServiceId;
    }

    public void setSourceServiceId(long sourceServiceId) {
        this.sourceServiceId = sourceServiceId;
    }

    public long getSourceProfileId() {
        return sourceProfileId;
    }

    public void setSourceProfileId(long sourceProfileId) {
        this.sourceProfileId = sourceProfileId;
    }

    public long getTargetServiceId() {
        return targetServiceId;
    }

    public void setTargetServiceId(long targetServiceId) {
        this.targetServiceId = targetServiceId;
    }

    public long getTargetProfileId() {
        return targetProfileId;
    }

    public void setTargetProfileId(long targetProfileId) {
        this.targetProfileId = targetProfileId;
    }
}
