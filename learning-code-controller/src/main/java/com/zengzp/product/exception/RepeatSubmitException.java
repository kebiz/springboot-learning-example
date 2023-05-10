package com.zengzp.product.exception;

import com.zengzp.product.service.StatusCode;
import lombok.Getter;

/**
 * @author ：zengzhipeng
 * @date ：Created in 2022/7/21 15:05
 * @description 重复提交异常类
 * @modified By：
 * @version: 1.0$
 */
@Getter
public class RepeatSubmitException extends BaseException{
    private StatusCode statusCode;
    public RepeatSubmitException(){super();}
    public RepeatSubmitException(String message) {
        super(message);
    }
    public RepeatSubmitException(StatusCode statusCode, String message) {
        super(message);
        this.statusCode=statusCode;
    }
}
