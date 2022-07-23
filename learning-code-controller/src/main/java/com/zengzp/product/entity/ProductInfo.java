package com.zengzp.product.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author ：zengzhipeng
 * @date ：Created in 2022/7/18 16:07
 * @description：商品实体表
 * @modified By：
 * @version: 1.0$
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("PRODUCT_INFO")
public class ProductInfo implements Serializable {
    //商品ID
    @TableId(value = "product_id",type = IdType.AUTO)
    private  long productId;
    //商品名称
    private String productName;
    // 商品价格
    private BigDecimal productPrice;
    // 上架状态
    private Integer productStatus;
}
