package com.zengzp.product.entity;

import lombok.Builder;
import lombok.Data;

/**
 * @author ：zengzhipeng
 * @date ：Created in 2022/11/8 16:36
 * @description：mapstruct转换源类
 * @modified By：
 * @version: 1$
 */
@Data
@Builder
public class TestSource {
    private long id;
    private long age;
    private String userNick;
    private  TestConfig testConfig;
}
