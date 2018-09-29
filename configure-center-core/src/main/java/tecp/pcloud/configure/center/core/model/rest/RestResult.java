package tecp.pcloud.configure.center.core.model.rest;

import tecp.pcloud.configure.center.core.exception.AppException;

/**
 * @ClassName RestResult
 * @Author pandong
 * @Date 2018/9/28 17:30
 **/
public class RestResult<T> {
    public static final int SUCCESS_CODE = 0;
    private int code;
    private String message;
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public <T extends AppException> void setException(T t) {
        this.code = t.getCode();
        this.message = t.getMessage();
    }
}
