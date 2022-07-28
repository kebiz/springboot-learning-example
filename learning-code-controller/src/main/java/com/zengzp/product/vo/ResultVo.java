package com.zengzp.product.vo;

import com.zengzp.product.constants.AppCode;
import com.zengzp.product.constants.ResultCode;
import com.zengzp.product.service.StatusCode;
import lombok.Data;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * @author ：zengzhipeng
 * @date ：Created in 2022/7/18 17:16
 * @description：结果集包装类
 * @modified By：
 * @version: 1.0$
 */
@Data
public class ResultVo {
    //返回码
    private int code;
    //返回信息
    private String msg;
    //返回对象
    private Object data;

    // 手动设置返回vo
    public ResultVo(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    // 默认返回成功状态码，数据对象
    public ResultVo(Object data) {
        this.code = ResultCode.SUCCESS.getCode();
        this.msg = ResultCode.SUCCESS.getMsg();
        this.data = data;
    }

    // 返回指定状态码，数据对象
    public ResultVo(StatusCode statusCode, Object data) {
        this.code = statusCode.getCode();
        this.msg = statusCode.getMsg();
        this.data = data;
    }


    // 只返回状态码
    public ResultVo(StatusCode statusCode) {
        this.code = statusCode.getCode();
        this.msg = statusCode.getMsg();
        this.data = null;
    }
    public static ModelAndView view(String view) {
        return new ModelAndView(view);
    }

    public static ModelAndView view(String view, Map<String, Object> model) {
        return new ModelAndView(view, model);
    }

    public static ModelAndView redirect(String view) {
        return new ModelAndView("redirect:" + view);
    }

    public static ModelAndView forward(String view) {
        return new ModelAndView("forward:" + view);
    }
    public static ResultVo success(Object object) {
        return new ResultVo(object);
    }

    public static ResultVo success() {
        return new ResultVo(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMsg(), null);
    }

    public static ResultVo fail(Object object) {
        return new ResultVo(ResultCode.FAILED.getCode(), ResultCode.FAILED.getMsg(), object);
    }

    public static ResultVo fail() {
        return new ResultVo(ResultCode.FAILED.getCode(), ResultCode.FAILED.getMsg(), null);
    }
    public static ResultVo fail(StatusCode statusCode) {
        return new ResultVo(statusCode.getCode(), statusCode.getMsg(), null);
    }
    public static ResultVo fail(String msg) {
        return new ResultVo(AppCode.LOGIN_ERROR.getCode(), msg, null);
    }
}
