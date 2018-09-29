package tecp.pcloud.configure.center.core.exception;

/**
 * @ClassName UserException
 * @Author pandong
 * @Date 2018/9/28 16:33
 **/
public class NoDataException extends DataException {
    public static final int DEFAULT_USER_ERROR_CODE = 3001;

    public NoDataException() {
        this(DEFAULT_USER_ERROR_CODE);
    }

    public NoDataException(int code) {
        super(code);
    }

    public NoDataException(String message) {
        this(message, DEFAULT_USER_ERROR_CODE);
    }

    public NoDataException(String message, int code) {
        super(message, code);
    }


    public NoDataException(String message, Throwable cause) {
        this(message, cause, DEFAULT_USER_ERROR_CODE);
    }

    public NoDataException(String message, Throwable cause, int code) {
        super(message, cause, code);
    }

    public NoDataException(Throwable cause) {
        this(cause, DEFAULT_USER_ERROR_CODE);
    }

    public NoDataException(Throwable cause, int code) {
        super(cause, code);
    }

    public NoDataException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        this(message, cause, enableSuppression, writableStackTrace, DEFAULT_USER_ERROR_CODE);
    }

    public NoDataException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, int code) {
        super(message, cause, enableSuppression, writableStackTrace, code);
    }
}
