package com.zengzp.cart.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.learning.code.common.model.StockDto;
import com.zengzp.cart.entity.Sku;

import java.util.List;

public interface SkuService extends IService<Sku> {
    void synStockDB(List<StockDto> stockDtos);
}
