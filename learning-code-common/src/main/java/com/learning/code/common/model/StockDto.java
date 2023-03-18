package com.learning.code.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author ：zengzhipeng
 * @date ：Created in 2023/3/16 11:04
 * @description：扣减库存参数
 * @modified By：
 * @version: 1$
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockDto implements Serializable {
    private Long skuId;
    private int saleNum;
}
