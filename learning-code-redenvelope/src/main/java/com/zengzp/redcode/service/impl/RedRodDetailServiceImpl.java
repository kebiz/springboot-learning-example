package com.zengzp.redcode.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zengzp.redcode.dao.RedRodDetailDao;
import com.zengzp.redcode.entity.RedRodDetail;
import com.zengzp.redcode.service.RedRodDetailService;
import org.springframework.stereotype.Service;

/**
 * 抢红包记录表(RedRodDetail)表服务实现类
 *
 * @author zeengzp
 * @since 2023-04-22 20:48:48
 */
@Service("redRodDetailService")
public class RedRodDetailServiceImpl extends ServiceImpl<RedRodDetailDao, RedRodDetail> implements RedRodDetailService {

}

