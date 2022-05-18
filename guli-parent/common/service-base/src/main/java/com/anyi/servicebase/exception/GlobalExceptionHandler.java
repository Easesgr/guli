package com.anyi.servicebase.exception;

import com.anyi.commonutils.R;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author 安逸i
 * @version 1.0
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public R error(Exception e){
        e.printStackTrace();
        return R.error().message("执行了全局异常处理");
    }

    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public R arithmeticException(Exception e){
        e.printStackTrace();
        return R.error().message("执行了特定异常处理");
    }
    @ExceptionHandler(GuliException.class)
    @ResponseBody
    public R myException(Exception e){
        e.printStackTrace();
        return R.error().message(e.getMessage());
    }
}
