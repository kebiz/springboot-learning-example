package com.zengzp.cart.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zengzp.cart.entity.Spu;
import com.zengzp.cart.mapper.SpuMapper;
import com.zengzp.cart.service.SpuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author ：zengzhipeng
 * @date ：Created in 2023/3/15 11:33
 * @description：spu实现类
 * @modified By：
 * @version: 1$
 */
@Service
@Slf4j
public class SpuServiceImpl extends ServiceImpl<SpuMapper, Spu> implements SpuService {
}
