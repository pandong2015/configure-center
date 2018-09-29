package tecp.pcloud.configure.center.core.model;

public interface StatusProperty {
    enum Status {
        DELETE, ACTIVE
    }

    String getStatus();
    void setStatus(String status);
}
