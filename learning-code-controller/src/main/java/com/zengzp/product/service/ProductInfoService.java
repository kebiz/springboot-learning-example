package com.zengzp.product.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zengzp.product.entity.ProductInfo;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author ：zengzhipeng
 * @date ：Created in 2022/7/18 16:31
 * @description：商品Service
 * @modified By：
 * @version: 1.0$
 */
public interface ProductInfoService extends IService<ProductInfo> {
    void retrySubmit(int code)throws Exception;

    /**
     * 批量更新产品信息
     */
    void  updateStudentWithThreads() throws InterruptedException;
    void updateProduct(List<ProductInfo> productInfoList, PlatformTransactionManager TransactionManager, CountDownLatch threadLatchs , AtomicBoolean isError, CountDownLatch mainLatchs);

}
