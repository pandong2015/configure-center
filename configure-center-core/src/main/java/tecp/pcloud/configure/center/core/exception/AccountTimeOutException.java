package tecp.pcloud.configure.center.core.exception;

/**
 * @ClassName UserException
 * @Author pandong
 * @Date 2018/9/28 16:33
 **/
public class AccountTimeOutException extends UserException {
    public static final int DEFAULT_USER_ERROR_CODE = 1003;

    public AccountTimeOutException() {
        this(DEFAULT_USER_ERROR_CODE);
    }

    public AccountTimeOutException(int code) {
        super(code);
    }

    public AccountTimeOutException(String message) {
        this(message, DEFAULT_USER_ERROR_CODE);
    }

    public AccountTimeOutException(String message, int code) {
        super(message, code);
    }


    public AccountTimeOutException(String message, Throwable cause) {
        this(message, cause, DEFAULT_USER_ERROR_CODE);
    }

    public AccountTimeOutException(String message, Throwable cause, int code) {
        super(message, cause, code);
    }

    public AccountTimeOutException(Throwable cause) {
        this(cause, DEFAULT_USER_ERROR_CODE);
    }

    public AccountTimeOutException(Throwable cause, int code) {
        super(cause, code);
    }

    public AccountTimeOutException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        this(message, cause, enableSuppression, writableStackTrace, DEFAULT_USER_ERROR_CODE);
    }

    public AccountTimeOutException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, int code) {
        super(message, cause, enableSuppression, writableStackTrace, code);
    }
}
