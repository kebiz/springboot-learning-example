package com.zengzp.project.model;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author ：zengzhipeng
 * @date ：Created in 2022/7/12 10:51
 * @description：购物车选项
 * @modified By：
 * @version: 1$
 */
@Data
public class Item {
    //商品ID
    private long id;
    //商品数量
    private int quantity;
    //商品单价
    private BigDecimal price;
    //商品优惠
    private BigDecimal couponPrice;
    //商品运费
    private BigDecimal deliveryPrice;
}
