package tecp.pcloud.configure.center.core.exception;

/**
 * @ClassName UserException
 * @Author pandong
 * @Date 2018/9/28 16:33
 **/
public class ClientException extends AppException {
    public static final int DEFAULT_USER_ERROR_CODE = 4000;

    public ClientException() {
        this(DEFAULT_USER_ERROR_CODE);
    }

    public ClientException(int code) {
        super(code);
    }

    public ClientException(String message) {
        this(message, DEFAULT_USER_ERROR_CODE);
    }

    public ClientException(String message, int code) {
        super(message, code);
    }


    public ClientException(String message, Throwable cause) {
        this(message, cause, DEFAULT_USER_ERROR_CODE);
    }

    public ClientException(String message, Throwable cause, int code) {
        super(message, cause, code);
    }

    public ClientException(Throwable cause) {
        this(cause, DEFAULT_USER_ERROR_CODE);
    }

    public ClientException(Throwable cause, int code) {
        super(cause, code);
    }

    public ClientException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        this(message, cause, enableSuppression, writableStackTrace, DEFAULT_USER_ERROR_CODE);
    }

    public ClientException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, int code) {
        super(message, cause, enableSuppression, writableStackTrace, code);
    }
}
