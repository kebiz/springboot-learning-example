package com.zengzp.redcode.constant;


import com.baomidou.mybatisplus.extension.api.IErrorCode;

public enum StatusCode implements IErrorCode {
    SUCCESS(0,"成功"),
    FAIL(1,"失败"),
    INVALIDGRANTTYPE(202,"非法的授权类型"),
    INVALIDPARAMS(201,"非法的参数"),
    ROD_FAIL(3,"红包已经抢完");

    private long code;
    private String msg;

    StatusCode(long code ,String msg){
        this.code=code;
        this.msg=msg;
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
