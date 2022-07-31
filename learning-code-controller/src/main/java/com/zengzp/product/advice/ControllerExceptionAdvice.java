package com.zengzp.product.advice;

import com.zengzp.product.constants.AppCode;
import com.zengzp.product.constants.ResultCode;
import com.zengzp.product.exception.APIException;
import com.zengzp.product.vo.ResultVo;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationException;
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
    @ExceptionHandler(ShiroException.class)
    public ResultVo doHandleShiroException(ShiroException se) {
        se.printStackTrace();
        if(se instanceof UnknownAccountException){
            return ResultVo.fail("该账号不存在");
        }
        else if(se instanceof LockedAccountException) {
            return ResultVo.fail("账户已锁定，请联系管理员");
        }
        else if(se instanceof IncorrectCredentialsException){
            return ResultVo.fail("密码错误，请重试");
        }
        else if(se instanceof AuthorizationException){
            return ResultVo.fail("没有相应权限，请联系管理员");
        }
        else {
            return ResultVo.fail("登录失败");
        }
 }
}
