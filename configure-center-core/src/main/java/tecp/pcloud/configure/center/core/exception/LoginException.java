package tecp.pcloud.configure.center.core.exception;

/**
 * @ClassName UserException
 * @Author pandong
 * @Date 2018/9/28 16:33
 **/
public class LoginException extends UserException {
    public static final int DEFAULT_USER_ERROR_CODE = 1001;

    public LoginException() {
        this(DEFAULT_USER_ERROR_CODE);
    }

    public LoginException(int code) {
        super(code);
    }

    public LoginException(String message) {
        this(message, DEFAULT_USER_ERROR_CODE);
    }

    public LoginException(String message, int code) {
        super(message, code);
    }


    public LoginException(String message, Throwable cause) {
        this(message, cause, DEFAULT_USER_ERROR_CODE);
    }

    public LoginException(String message, Throwable cause, int code) {
        super(message, cause, code);
    }

    public LoginException(Throwable cause) {
        this(cause, DEFAULT_USER_ERROR_CODE);
    }

    public LoginException(Throwable cause, int code) {
        super(cause, code);
    }

    public LoginException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        this(message, cause, enableSuppression, writableStackTrace, DEFAULT_USER_ERROR_CODE);
    }

    public LoginException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, int code) {
        super(message, cause, enableSuppression, writableStackTrace, code);
    }
}
