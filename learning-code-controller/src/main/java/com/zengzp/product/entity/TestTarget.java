package com.zengzp.product.entity;

import lombok.Builder;
import lombok.Data;

/**
 * @author ：zengzhipeng
 * @date ：Created in 2022/11/8 16:37
 * @description：mapstruct目标类
 * @modified By：
 * @version: 1$
 */
@Data
@Builder
public class TestTarget {
    private long id;
    private Integer age;
    private String nick;
}
