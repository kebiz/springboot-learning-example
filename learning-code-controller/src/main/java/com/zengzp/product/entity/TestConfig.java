package com.zengzp.product.entity;

import lombok.Builder;
import lombok.Data;

/**
 * @author ：zengzhipeng
 * @date ：Created in 2022/11/9 16:17
 * @description：config类
 * @modified By：
 * @version: 1$
 */
@Data
@Builder
public class TestConfig {
    private long id;
    private String  idCard;
    private String telPhone;
}
