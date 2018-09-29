package tecp.pcloud.configure.center.core.exception;

/**
 * @ClassName UserException
 * @Author pandong
 * @Date 2018/9/28 16:33
 **/
public class DataException extends AppException {
    public static final int DEFAULT_USER_ERROR_CODE = 3000;

    public DataException() {
        this(DEFAULT_USER_ERROR_CODE);
    }

    public DataException(int code) {
        super(code);
    }

    public DataException(String message) {
        this(message, DEFAULT_USER_ERROR_CODE);
    }

    public DataException(String message, int code) {
        super(message, code);
    }


    public DataException(String message, Throwable cause) {
        this(message, cause, DEFAULT_USER_ERROR_CODE);
    }

    public DataException(String message, Throwable cause, int code) {
        super(message, cause, code);
    }

    public DataException(Throwable cause) {
        this(cause, DEFAULT_USER_ERROR_CODE);
    }

    public DataException(Throwable cause, int code) {
        super(cause, code);
    }

    public DataException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        this(message, cause, enableSuppression, writableStackTrace, DEFAULT_USER_ERROR_CODE);
    }

    public DataException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, int code) {
        super(message, cause, enableSuppression, writableStackTrace, code);
    }
}
