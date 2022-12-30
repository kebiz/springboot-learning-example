package com.zengzp.cart.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author ：zengzhipeng
 * @date ：Created in 2022/12/30 15:17
 * @description：购物车
 * @modified By：
 * @version: 1$
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem implements Serializable {
    //id
    private long id;
    //skuId
    private long skuId;
    //spuId
    private long spuId;
    //订单ID
    private String orderId;
    //一级分类Id
    private long category1Id;
    //二级分类Id
    private long category2Id;
    //三级分类Id
    private long category3Id;
    //名称
    private String name;
    //数量
    private Integer num;
    //价格
    private Double price;
    //重量
    private Double weight;
    //总价
    private Double totalMoney;
    //实付金额
    private Double payMoney;
    //图片地址
    private String image;
    //运费
    private Double postFee;
    //是否退货
    private String isReturn;
}
