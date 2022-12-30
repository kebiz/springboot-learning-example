package com.zengzp.product.service;

/**
 * @author ：zengzhipeng
 * @date ：Created in 2022/8/17 9:52
 * @description：扣减库存回调类
 * @modified By：
 * @version: 1.0$
 */
public interface IStockCallBack {
    /**
     * 获取库存
     * @return
     */
    int getStock();
}
