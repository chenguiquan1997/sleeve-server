package com.quan.windsleeve.core;

import com.quan.windsleeve.core.configuration.ExceptionCodesConfiguration;
import com.quan.windsleeve.exception.http.HttpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.util.List;

/**
 * 全局异常处理类，目的是为了向前端返回统一的异常格式
 */
@ControllerAdvice//声明当前类是全局异常处理类
public class GlobalExceptionAdvice {

    //注入当前类的目的是为了 异常code文件中的数据
    @Autowired
    private ExceptionCodesConfiguration codesConfiguration;
    /**
     * 该注解表示一个处理异常的执行器，内部的value表示当前方法处理异常的类型，Exception表示
     * 通用异常类型，当前方法不仅仅可以拦截抛出的exception类型，还可以拦截Exception的子类
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    //该注解可以返回自定义的服务器响应的状态码，如果不添加该注解，那么有可能服务器发生异常，但是
    //前端收到的响应状态码还是200，这样是不对的
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public UnifyResponse handlerException(HttpServletRequest req,Exception e) {
        System.out.println("exception 未知异常");
        String method = req.getMethod();
        String uri = req.getRequestURI();
        System.out.println(e);
        /**
         * 如果不在Unify类中添加Getter()；那么message对象在返回到前端数据时，就得不到需要序列化的
         * 对象，所以就会报错
         */
        UnifyResponse message = new UnifyResponse(99999,"服务器异常",method+" "+uri);
        return message;
    }

    /**
     * 如果同一个异常，同时属于多个异常拦截器的范围内，那么如果第一个拦截器已经拦截并处理了，
     * 那么其他的拦截器就不会拦截
     * 当前方法是处理已知异常的处理器
     */
    @ExceptionHandler(value = HttpException.class)
    public ResponseEntity<UnifyResponse> handlerHttpException(HttpServletRequest req, HttpException e) {
        System.out.println("httpException");
        String method = req.getMethod();
        String uri = req.getRequestURI();
        System.out.println(e);
        /*ResponseEntity不仅可以返回自定义的状态码，还可以返回HttpHeaders(请求头和响应头)
         在ResponseEntity中我们需要传入三个参数：(1)UnifyResponse (2)HttpHeaders(3)
         */
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        UnifyResponse message = new UnifyResponse(e.getCode(),codesConfiguration.getMessage(e.getCode()),method+" "+uri);
        HttpStatus status = HttpStatus.resolve(e.getHttpStatusCode());
        ResponseEntity<UnifyResponse> entity = new ResponseEntity(message,headers,status);
        return entity;
    }

    //MethodArgumentNotValidException主要用于方法参数异常的校验，如果方法参数传入错误，那么会自动抛出异常
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(code= HttpStatus.BAD_REQUEST)
    @ResponseBody
    public UnifyResponse methodArguementException(HttpServletRequest req,MethodArgumentNotValidException e) {
        String method = req.getMethod();
        String uri = req.getRequestURI();
        List<ObjectError> errorList = e.getBindingResult().getAllErrors();
        String errorMsg = formatAllErrorMessages(errorList);
        UnifyResponse unifyResponse = new UnifyResponse(10002,errorMsg,method+" "+uri);
        return unifyResponse;
    }

    //ConstraintViolationException主要用于校验url路径中携带的参数是否正确，如果错误，需要抛出异常
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(code= HttpStatus.BAD_REQUEST)
    @ResponseBody
    public UnifyResponse handlerConstraintException(HttpServletRequest req, ConstraintViolationException e){
        String requestUrl = req.getRequestURI();
        String method = req.getMethod();
        String message = e.getMessage();

        return new UnifyResponse(10003, message, method + " " + requestUrl);
    }

    /**
     * 获取错误列表中的所有错误信息
     * @param errors
     * @return
     */
    private String formatAllErrorMessages(List<ObjectError> errors) {
        StringBuffer errorMsg = new StringBuffer();
        errors.forEach(error ->
                errorMsg.append(error.getDefaultMessage()).append(';')
        );
        return errorMsg.toString();
    }

}
