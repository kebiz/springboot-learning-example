package com.learning.code.common.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.learning.code.common.util.JsonSerializerUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author ：zengzhipeng
 * @date ：Created in 2022/12/30 15:17
 * @description：订单商品项
 * @modified By：
 * @version: 1$
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_order_item")
public class OrderItem implements Serializable {
    //id
    @TableId(value = "id",type = IdType.INPUT)
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
    @JsonSerialize(using = JsonSerializerUtils.class)
    private Double price;
    //重量
    @JsonSerialize(using = JsonSerializerUtils.class)
    private Double weight;
    //总价
    @JsonSerialize(using = JsonSerializerUtils.class)
    private Double totalMoney;
    //实付金额
    @JsonSerialize(using = JsonSerializerUtils.class)
    private Double payMoney;
    //图片地址
    private String image;
    //运费
    @JsonSerialize(using = JsonSerializerUtils.class)
    private Double postFee;
    //是否退货
    private String isReturn;
}
