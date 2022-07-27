package com.zengzp.product.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zengzp.product.entity.ProductInfo;

/**
 * @author ：zengzhipeng
 * @date ：Created in 2022/7/18 16:31
 * @description：商品Service
 * @modified By：
 * @version: 1.0$
 */
public interface ProductInfoService extends IService<ProductInfo> {
    void retrySubmit(int code)throws Exception;
}
