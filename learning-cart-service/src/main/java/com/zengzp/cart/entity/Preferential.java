package com.zengzp.cart.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author ：zengzhipeng
 * @date ：Created in 2023/1/6 14:28
 * @description：优惠规则
 * @modified By：
 * @version: 1$
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Preferential implements Serializable {
    private Long id;
    /**
     * 分类ID
     */
    private Long categoryId;
    /**
     * 购买金额
     */
    private Double buyMoney;
    /**
     * 优惠金额
     */
    private Double preMoney;
    /**
     * 开始时间
     */
    private Date startTime;
    /**
     * 结束时间
     */
    private Date endTime;
    /**
     * 状态： 启用/禁用
     */
    private String status;
    /**
     * 类型：翻倍/不翻倍
     */
    private String type;

}
