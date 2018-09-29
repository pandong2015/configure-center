package tecp.pcloud.configure.center.core.model;

/**
 * @ClassName PKProperty
 * @Author pandong
 * @Date 2018/9/28 13:37
 **/
public interface PKProperty<T> {
    T getPK();
    void setPK(T pk);
}
