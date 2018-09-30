package tecp.pcloud.configure.center.core.exception;

/**
 * @ClassName UserException
 * @Author pandong
 * @Date 2018/9/28 16:33
 **/
public class InitKeyException extends SecurityException {
    public static final int DEFAULT_USER_ERROR_CODE = 2001;

    public InitKeyException() {
        this(DEFAULT_USER_ERROR_CODE);
    }

    public InitKeyException(int code) {
        super(code);
    }

    public InitKeyException(String message) {
        this(message, DEFAULT_USER_ERROR_CODE);
    }

    public InitKeyException(String message, int code) {
        super(message, code);
    }


    public InitKeyException(String message, Throwable cause) {
        this(message, cause, DEFAULT_USER_ERROR_CODE);
    }

    public InitKeyException(String message, Throwable cause, int code) {
        super(message, cause, code);
    }

    public InitKeyException(Throwable cause) {
        this(cause, DEFAULT_USER_ERROR_CODE);
    }

    public InitKeyException(Throwable cause, int code) {
        super(cause, code);
    }

    public InitKeyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        this(message, cause, enableSuppression, writableStackTrace, DEFAULT_USER_ERROR_CODE);
    }

    public InitKeyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, int code) {
        super(message, cause, enableSuppression, writableStackTrace, code);
    }
}
