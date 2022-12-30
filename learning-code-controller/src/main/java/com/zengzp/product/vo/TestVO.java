package com.zengzp.product.vo;

import lombok.Builder;
import lombok.Data;

/**
 * @author ：zengzhipeng
 * @date ：Created in 2022/11/9 15:22
 * @description：vo对象
 * @modified By：
 * @version: 1$
 */
@Builder
@Data
public class TestVO {
    private long id;
    private String age;
    private String userNick;
    private String idCard;
    private String telPhone;
}
