package com.learning.code.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 生成订单失败消息体
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SynStockDBMessage implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 订单ID
     */
    private Long orderId;

    private List<StockDto> stockDtoList;

}
