package com.zengzp.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zengzp.product.dao.ProductInfoMapper;
import com.zengzp.product.entity.ProductInfo;
import com.zengzp.product.service.ProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

/**
 * @author ：zengzhipeng
 * @date ：Created in 2022/7/18 16:36
 * @description：商品Servcie实现类
 * @modified By：
 * @version: 1.0$
 */
@Service
public class ProductInfoServiceImpl extends ServiceImpl<ProductInfoMapper,ProductInfo> implements ProductInfoService {
    @Override
    @Retryable(value = Exception.class,maxAttempts = 3,backoff = @Backoff(delay = 2000,multiplier = 1.5))
    public void retrySubmit(int code) throws Exception{
        System.out.println("========开始调用方法======");
        int i=0;
        if(i==0){
            throw new Exception("出现异常");
        }
    }
    @Recover
    public void recover(Exception e, int code){
        System.out.println("回调方法执行！！！！");
        //记日志到数据库 或者调用其余的方法
    }
}
