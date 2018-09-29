package tecp.pcloud.configure.center.core.model.vo.request.parameter;

/**
 * @ClassName ServiceSearchParameters
 * @Author pandong
 * @Date 2018/9/28 17:43
 **/
public class ServiceSearchParameters extends NameSearchParameters {
    private long userId;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
