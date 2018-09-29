package tecp.pcloud.configure.center.core.exception;

/**
 * @ClassName UserException
 * @Author pandong
 * @Date 2018/9/28 16:33
 **/
public class DESException extends SecurityException {
    public static final int DEFAULT_USER_ERROR_CODE = 2001;

    public DESException() {
        this(DEFAULT_USER_ERROR_CODE);
    }

    public DESException(int code) {
        super(code);
    }

    public DESException(String message) {
        this(message, DEFAULT_USER_ERROR_CODE);
    }

    public DESException(String message, int code) {
        super(message, code);
    }


    public DESException(String message, Throwable cause) {
        this(message, cause, DEFAULT_USER_ERROR_CODE);
    }

    public DESException(String message, Throwable cause, int code) {
        super(message, cause, code);
    }

    public DESException(Throwable cause) {
        this(cause, DEFAULT_USER_ERROR_CODE);
    }

    public DESException(Throwable cause, int code) {
        super(cause, code);
    }

    public DESException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        this(message, cause, enableSuppression, writableStackTrace, DEFAULT_USER_ERROR_CODE);
    }

    public DESException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, int code) {
        super(message, cause, enableSuppression, writableStackTrace, code);
    }
}
