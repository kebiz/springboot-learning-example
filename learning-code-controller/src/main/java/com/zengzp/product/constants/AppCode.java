package com.zengzp.product.constants;

import com.zengzp.product.service.StatusCode;
import lombok.Getter;

@Getter
public enum AppCode implements StatusCode {
    APP_ERROR(2000,"APP异常"),
    PRICE_ERROR(2001,"价格异常"),
    LOGIN_ERROR(2003,"登录异常"),
    REAPEAT_SUBMIT_ERROR(50001,"重复提交异常");
    private int code;
    private String msg;
    AppCode(int code,String msg){
        this.code=code;
        this.msg=msg;
    }
}
