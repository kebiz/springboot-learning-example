package com.zengzp.product.exception;

import com.zengzp.product.constants.ResultCode;
import com.zengzp.product.service.StatusCode;
import com.zengzp.product.vo.ResultVo;
import lombok.Getter;

/**
 * @author ：zengzhipeng
 * @date ：Created in 2022/7/21 15:05
 * @description：API异常类
 * @modified By：
 * @version: 1.0$
 */
public class APIException extends BaseException{
    public APIException(){super();}
    public APIException(String message) {
        super(message);
    }
}
