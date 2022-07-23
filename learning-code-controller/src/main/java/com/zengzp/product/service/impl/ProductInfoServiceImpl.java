package com.zengzp.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zengzp.product.dao.ProductInfoMapper;
import com.zengzp.product.entity.ProductInfo;
import com.zengzp.product.service.ProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
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
}
