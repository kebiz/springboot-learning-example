package com.zengzp.cart.service.impl;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.learning.code.common.model.StockDto;
import com.zengzp.cart.contant.CacheKey;
import com.zengzp.cart.entity.Sku;
import com.zengzp.cart.mapper.SkuMapper;
import com.zengzp.cart.service.SkuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ：zengzhipeng
 * @date ：Created in 2023/3/15 11:38
 * @description：sku实现类
 * @modified By：
 * @version: 1$
 */
@Slf4j
@Service
public class SkuServiceImpl extends ServiceImpl<SkuMapper, Sku> implements SkuService {
    @Autowired
    private SkuMapper skuMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void synStockDB(List<StockDto> stockDtos){
            Assert.notEmpty(stockDtos,"同步数据为空");
            stockDtos.stream().forEach(stockDto -> {
                Sku sku = this.getById(stockDto.getSkuId());
                skuMapper.updateByStock(stockDto.getSkuId(),stockDto.getSaleNum(),sku.getVersion());
                //同步商品缓存
                redisTemplate.boundHashOps(CacheKey.CART_LIST.toString()).put(sku.getId(),sku);
            });
    }
}
