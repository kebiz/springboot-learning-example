package com.zengzp.product.advice;

import com.zengzp.product.constants.AppCode;
import com.zengzp.product.constants.ResultCode;
import com.zengzp.product.exception.APIException;
import com.zengzp.product.vo.ResultVo;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author ：zengzhipeng
 * @date ：Created in 2022/7/20 15:28
 * @description：异常拦截类
 * @modified By：
 * @version: 1.0$
 */
@RestControllerAdvice
public class ControllerExceptionAdvice {
    @ExceptionHandler({BindException.class})
    public ResultVo MethodArgumentNotValidExceptionHandler(BindException ex){
        ObjectError objectError = ex.getBindingResult().getAllErrors().get(0);
        return new ResultVo(ResultCode.VALIDATE_ERROR,objectError.getDefaultMessage());

    }
    @ExceptionHandler({APIException.class})
    public ResultVo APIExceptionHandler(APIException ex){
        return new ResultVo(AppCode.APP_ERROR,ex.getMessage());

    }
}
