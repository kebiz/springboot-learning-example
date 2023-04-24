package com.zengzp.redcode.constant;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Author zengzp
 * @Date 2023/4/22 21:04
 * @Version 1.0
 **/
@Data
public class BaseResponse<T> {
    //状态码
    private long code;
    //描述信息
    private String msg;
    //响应数据
    private T data;

    public BaseResponse(StatusCode statusCode){
        this.code=statusCode.getCode();
        this.msg=statusCode.getMsg();
    }

    public BaseResponse(long code ,String msg,T data){
        this.code=code;
        this.msg=msg;
        this.data=data;
    }
}
