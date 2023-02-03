package com.zengzp.cart.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author ：zengzhipeng
 * @date ：Created in 2023/1/3 11:54
 * @description：购物车service
 * @modified By：
 * @version: 1$
 */
public interface  CartService {
    /**
     * 根据用户名在缓存中查询购物车
     * @param userName
     * @return
     */
    List<Map<String, Object>> findCartList(String userName) ;

    /**
     * 添加到购物车
     * @param userName 用户ID
     * @param skuId skuId
     * @param num   数量
     * @return
     */
    boolean addCart(String userName ,long skuId,int num);

    /**
     * 购物车选中状态
     * @param userName 当前用户
     * @param skuId
     * @param checked 是否选中
     */
    void  updateChecked(String userName,long skuId,boolean checked);

    /**
     * 删除购物车选项
     * @param userName
     */
    void  deleteCartItem(String userName);

    /**
     * 计算购物车选中商品的总优惠金额
     * @param userName
     * @return
     */
    double reduceCartPreMoney(String userName);


}
