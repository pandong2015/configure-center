package tecp.pcloud.configure.center.core.exception;

/**
 * @ClassName UserException
 * @Author pandong
 * @Date 2018/9/28 16:33
 **/
public class ConnectionException extends ClientException {
    public static final int DEFAULT_USER_ERROR_CODE = 4001;

    public ConnectionException() {
        this(DEFAULT_USER_ERROR_CODE);
    }

    public ConnectionException(int code) {
        super(code);
    }

    public ConnectionException(String message) {
        this(message, DEFAULT_USER_ERROR_CODE);
    }

    public ConnectionException(String message, int code) {
        super(message, code);
    }


    public ConnectionException(String message, Throwable cause) {
        this(message, cause, DEFAULT_USER_ERROR_CODE);
    }

    public ConnectionException(String message, Throwable cause, int code) {
        super(message, cause, code);
    }

    public ConnectionException(Throwable cause) {
        this(cause, DEFAULT_USER_ERROR_CODE);
    }

    public ConnectionException(Throwable cause, int code) {
        super(cause, code);
    }

    public ConnectionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        this(message, cause, enableSuppression, writableStackTrace, DEFAULT_USER_ERROR_CODE);
    }

    public ConnectionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, int code) {
        super(message, cause, enableSuppression, writableStackTrace, code);
    }
}
