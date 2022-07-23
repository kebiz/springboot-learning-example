package com.zengzp.product.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author ：zengzhipeng
 * @date ：Created in 2022/7/18 15:44
 * @description：商品信息
 * @modified By：
 * @version: 1.0$
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ProductInfoVO {
    // 商品名称
    @NotNull(message = "商品名称不允许为空")
    private String productName;
    // 商品价格
    @Min(value = 0, message = "商品价格不允许为负数")
    private BigDecimal productPrice;
    // 上架状态
    private Integer productStatus;
}
