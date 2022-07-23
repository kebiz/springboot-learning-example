package com.zengzp.project.model;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
@Inherited
public @interface BankAPIFeild {
    int order() default -1;
    int length() default -1;
    String type() default "";
}
