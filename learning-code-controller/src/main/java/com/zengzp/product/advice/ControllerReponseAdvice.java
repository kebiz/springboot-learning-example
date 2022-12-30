package com.zengzp.product.advice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zengzp.product.annotation.NotRestControllerAdvice;
import com.zengzp.product.constants.ResultCode;
import com.zengzp.product.exception.APIException;
import com.zengzp.product.vo.ResultVo;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @author ：zengzhipeng
 * @date ：Created in 2022/7/21 14:36
 * @description：请求响应统一包装
 * @modified By：
 * @version: 1.0$
 */
@RestControllerAdvice(basePackages = {"com.zengzp.product"})
public class ControllerReponseAdvice implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return !methodParameter.getParameterType().isAssignableFrom(ResultVo.class)
                &&!methodParameter.hasMethodAnnotation(NotRestControllerAdvice.class);
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        //String不能直接进行包装
        if(methodParameter.getGenericParameterType().equals(String.class)){
            ObjectMapper objectMapper=new ObjectMapper();
            try {
                return objectMapper.writeValueAsString(ResultVo.success(o));

            } catch (Exception e) {
                throw new APIException(e.getMessage()+"订单号");
            }
        }
        return ResultVo.success(o);
    }
}
