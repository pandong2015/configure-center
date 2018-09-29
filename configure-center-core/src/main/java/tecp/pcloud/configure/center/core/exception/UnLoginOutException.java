package tecp.pcloud.configure.center.core.exception;

/**
 * @ClassName UserException
 * @Author pandong
 * @Date 2018/9/28 16:33
 **/
public class UnLoginOutException extends UserException {
    public static final int DEFAULT_USER_ERROR_CODE = 1002;

    public UnLoginOutException() {
        this(DEFAULT_USER_ERROR_CODE);
    }

    public UnLoginOutException(int code) {
        super(code);
    }

    public UnLoginOutException(String message) {
        this(message, DEFAULT_USER_ERROR_CODE);
    }

    public UnLoginOutException(String message, int code) {
        super(message, code);
    }


    public UnLoginOutException(String message, Throwable cause) {
        this(message, cause, DEFAULT_USER_ERROR_CODE);
    }

    public UnLoginOutException(String message, Throwable cause, int code) {
        super(message, cause, code);
    }

    public UnLoginOutException(Throwable cause) {
        this(cause, DEFAULT_USER_ERROR_CODE);
    }

    public UnLoginOutException(Throwable cause, int code) {
        super(cause, code);
    }

    public UnLoginOutException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        this(message, cause, enableSuppression, writableStackTrace, DEFAULT_USER_ERROR_CODE);
    }

    public UnLoginOutException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, int code) {
        super(message, cause, enableSuppression, writableStackTrace, code);
    }
}
