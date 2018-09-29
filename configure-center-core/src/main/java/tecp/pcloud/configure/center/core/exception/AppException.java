package tecp.pcloud.configure.center.core.exception;

/**
 * @ClassName AppException
 * @Author pandong
 * @Date 2018/9/28 16:32
 **/
public class AppException extends RuntimeException {
    private int code;

    public AppException(int code) {
        this.code = code;
    }

    public AppException(String message, int code) {
        super(message);
        this.code = code;
    }

    public AppException(String message, Throwable cause, int code) {
        super(message, cause);
        this.code = code;
    }

    public AppException(Throwable cause, int code) {
        super(cause);
        this.code = code;
    }

    public AppException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, int code) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
