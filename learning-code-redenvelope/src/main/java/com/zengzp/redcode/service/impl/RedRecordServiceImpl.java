package com.zengzp.redcode.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zengzp.redcode.dao.RedRecordDao;
import com.zengzp.redcode.entity.RedRecord;
import com.zengzp.redcode.service.RedRecordService;
import org.springframework.stereotype.Service;

/**
 * 发红包记录表(RedRecord)表服务实现类
 *
 * @author zeengzp
 * @since 2023-03-19 16:32:44
 */
@Service("redRecordService")
public class RedRecordServiceImpl extends ServiceImpl<RedRecordDao, RedRecord> implements RedRecordService {

}

