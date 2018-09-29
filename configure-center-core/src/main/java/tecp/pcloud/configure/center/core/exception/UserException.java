package tecp.pcloud.configure.center.core.exception;

/**
 * @ClassName UserException
 * @Author pandong
 * @Date 2018/9/28 16:33
 **/
public class UserException extends AppException {
    public static final int DEFAULT_USER_ERROR_CODE = 1000;

    public UserException() {
        this(DEFAULT_USER_ERROR_CODE);
    }

    public UserException(int code) {
        super(code);
    }

    public UserException(String message) {
        this(message, DEFAULT_USER_ERROR_CODE);
    }

    public UserException(String message, int code) {
        super(message, code);
    }


    public UserException(String message, Throwable cause) {
        this(message, cause, DEFAULT_USER_ERROR_CODE);
    }

    public UserException(String message, Throwable cause, int code) {
        super(message, cause, code);
    }

    public UserException(Throwable cause) {
        this(cause, DEFAULT_USER_ERROR_CODE);
    }

    public UserException(Throwable cause, int code) {
        super(cause, code);
    }

    public UserException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        this(message, cause, enableSuppression, writableStackTrace, DEFAULT_USER_ERROR_CODE);
    }

    public UserException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, int code) {
        super(message, cause, enableSuppression, writableStackTrace, code);
    }
}
