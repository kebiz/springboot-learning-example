package com.zengzp.product.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface NoRepeatSubmit {
    /**
     * 多长时间 ，默认5秒
     */
    long lockTime() default 5000L;
    /**
     * 最大访问次数
     * @return 最大访问次数
     */
    long maxTime() default 3L;

    /**
     * 禁用时长，单位/秒
     * @return 禁用时长
     */
    long forbiddenTime() default 120L;
}
