package com.zengzp.redcode.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zengzp.redcode.dao.RedDetailDao;
import com.zengzp.redcode.entity.RedDetail;
import com.zengzp.redcode.service.RedDetailService;
import org.springframework.stereotype.Service;

/**
 * 红包明细金额(RedDetail)表服务实现类
 *
 * @author zeengzp
 * @since 2023-04-22 20:38:48
 */
@Service("redDetailService")
public class RedDetailServiceImpl extends ServiceImpl<RedDetailDao, RedDetail> implements RedDetailService {

}

