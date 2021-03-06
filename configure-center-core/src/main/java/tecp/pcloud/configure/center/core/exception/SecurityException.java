package tecp.pcloud.configure.center.core.exception;

/**
 * @ClassName UserException
 * @Author pandong
 * @Date 2018/9/28 16:33
 **/
public class SecurityException extends AppException {
    public static final int DEFAULT_USER_ERROR_CODE = 2000;

    public SecurityException() {
        this(DEFAULT_USER_ERROR_CODE);
    }

    public SecurityException(int code) {
        super(code);
    }

    public SecurityException(String message) {
        this(message, DEFAULT_USER_ERROR_CODE);
    }

    public SecurityException(String message, int code) {
        super(message, code);
    }


    public SecurityException(String message, Throwable cause) {
        this(message, cause, DEFAULT_USER_ERROR_CODE);
    }

    public SecurityException(String message, Throwable cause, int code) {
        super(message, cause, code);
    }

    public SecurityException(Throwable cause) {
        this(cause, DEFAULT_USER_ERROR_CODE);
    }

    public SecurityException(Throwable cause, int code) {
        super(cause, code);
    }

    public SecurityException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        this(message, cause, enableSuppression, writableStackTrace, DEFAULT_USER_ERROR_CODE);
    }

    public SecurityException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, int code) {
        super(message, cause, enableSuppression, writableStackTrace, code);
    }
}
