package tech.pcloud.configure.center.server.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import tecp.pcloud.configure.center.core.exception.AppException;
import tecp.pcloud.configure.center.core.model.rest.RestResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @ClassName AppController
 * @Author pandong
 * @Date 2018/9/28 17:32
 **/
public class AppController {
    protected Logger log = LoggerFactory.getLogger(getClass());

    public RestResult success() {
        return success(null);
    }

    public <T> RestResult<T> success(T data) {
        return buildResult(RestResult.SUCCESS_CODE, null, data);
    }

    public <T> RestResult<T> fail(AppException exception) {
        return fail(exception.getCode(), exception.getMessage());
    }

    public <T> RestResult<T> fail(int code, String message) {
        return fail(code, message, null);
    }

    public <T> RestResult<T> fail(AppException exception, T data) {
        return fail(exception.getCode(), exception.getMessage(), data);
    }

    public <T> RestResult<T> fail(int code, String message, T data) {
        return buildResult(code, message, data);
    }

    public <T> RestResult<T> buildResult(int code, String message, T data) {
        RestResult<T> restResult = new RestResult<T>();
        restResult.setCode(code);
        restResult.setMessage(message);
        restResult.setData(data);
        return restResult;
    }

    protected HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    protected HttpServletResponse getResponse() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
    }

    protected HttpSession getSession() {
        return getRequest().getSession();
    }

    protected void addSessionAttribute(String name, Object value) {
        getSession().setAttribute(name, value);
    }

    protected void removeSessionAttribute(String name) {
        getSession().removeAttribute(name);
    }

    protected Object setSessionAttribute(String name) {
        return getSession().getAttribute(name);
    }

    @CrossOrigin("*")
    @ResponseBody
    @ExceptionHandler({Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public RestResult exception(Exception e) {
        log.error(e.getMessage(), e);
        Integer status = HttpStatus.INTERNAL_SERVER_ERROR.value();
        String message = e.getMessage();
        return fail(status, message);
    }

    @CrossOrigin("*")
    @ResponseBody
    @ExceptionHandler({RuntimeException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RestResult exception(RuntimeException e) {
        log.error(e.getMessage(), e);
        Integer status = HttpStatus.BAD_REQUEST.value();
        String message = e.getMessage();
        return fail(status, message);
    }

    @CrossOrigin("*")
    @ResponseBody
    @ExceptionHandler({AppException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public RestResult exception(AppException e) {
        log.error(e.getMessage(), e);
        return fail(e);
    }

    @CrossOrigin("*")
    @ResponseBody
    @ExceptionHandler({HttpMessageNotReadableException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public RestResult exception(HttpMessageNotReadableException e) {
        log.error(e.getMessage(), e);
        Integer status = HttpStatus.BAD_REQUEST.value();
        String message = e.getMessage();
        return fail(status, message);
    }
}
