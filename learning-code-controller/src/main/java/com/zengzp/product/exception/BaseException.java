package com.zengzp.product.exception;

import com.zengzp.product.constants.ResultCode;
import com.zengzp.product.service.StatusCode;
import lombok.Getter;

/**
 * @author ：zengzhipeng
 * @date ：Created in 2022/7/21 15:05
 * @description：异常基类
 * @modified By：
 * @version: 1.0$
 */
@Getter
public class BaseException extends RuntimeException{
    private StatusCode statusCode;
    public BaseException(){
this.statusCode=ResultCode.RESPONSE_PACK_ERROR;
    }
   public BaseException(StatusCode statusCode){
        this.statusCode=statusCode;
    }
    public BaseException(StatusCode statusCode, String message){
        super(message);
        this.statusCode=statusCode;
    }
    public BaseException(String message){
        super(message);
        this.statusCode=statusCode;
    }
    public BaseException(StatusCode statusCode, String message, Throwable cause) {
        super(message, cause);
        this.statusCode=statusCode;
    }
}
