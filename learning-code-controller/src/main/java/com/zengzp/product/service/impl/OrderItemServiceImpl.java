package com.zengzp.product.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.learning.code.common.model.OrderItem;
import com.zengzp.product.dao.mapper.OrderItemMapper;


import com.zengzp.product.service.OrderItemService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

/**
 *
 */
@Service
@Slf4j
public class OrderItemServiceImpl extends ServiceImpl<OrderItemMapper, OrderItem>
        implements OrderItemService {
}




