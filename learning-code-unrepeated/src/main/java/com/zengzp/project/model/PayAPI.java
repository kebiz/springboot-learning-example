package com.zengzp.project.model;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author ：zengzhipeng
 * @date ：Created in 2022/7/14 10:53
 * @description：用户支付API
 * @modified By：
 * @version: 1$
 */
@BankAPI(url = "/bank/pay",desc = "支付API")
@Data
public class PayAPI extends AbstractAPI{
    @BankAPIFeild(order = 1,length = 20,type = "N")
    private long userId;
    @BankAPIFeild(order = 2,length = 10,type = "M")
    private BigDecimal amount;
}
