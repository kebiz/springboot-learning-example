package com.zengzp.project.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zengzp.project.util.CustomerBigDecimalSerialize;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ：zengzhipeng
 * @date ：Created in 2022/7/12 10:55
 * @description：购物车
 * @modified By：
 * @version: 1$
 */
@Data
public class Car {
    //商品清单
    private List<Item> items = new ArrayList<Item>();
    //总优惠
    @JsonSerialize(using = CustomerBigDecimalSerialize.class)
    private BigDecimal totalDiscount;
    //商品总价
    @JsonSerialize(using = CustomerBigDecimalSerialize.class)
    private BigDecimal totalItemPrice;
    //总运费
    @JsonSerialize(using = CustomerBigDecimalSerialize.class)
    private BigDecimal totalDeliveryPrice;
    //应付总价
    @JsonSerialize(using = CustomerBigDecimalSerialize.class)
    private BigDecimal payPrice;
}
