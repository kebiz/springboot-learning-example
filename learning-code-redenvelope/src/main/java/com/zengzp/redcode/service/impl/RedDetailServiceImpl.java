package com.zengzp.redcode.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zengzp.redcode.dao.RedDetailDao;
import com.zengzp.redcode.dto.RedPacketDto;
import com.zengzp.redcode.entity.RedDetail;
import com.zengzp.redcode.service.RedDetailService;
import org.checkerframework.checker.units.qual.C;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 红包明细金额(RedDetail)表服务实现类
 *
 * @author zeengzp
 * @since 2023-04-22 20:38:48
 */
@Service("redDetailService")
public class RedDetailServiceImpl extends ServiceImpl<RedDetailDao, RedDetail> implements RedDetailService {
    @Override
    @Async
    public void recordRedPacket(Integer recordId, List<Integer> list) throws Exception {
    if(recordId==null || CollectionUtil.isEmpty(list)){
        throw new Exception("参数异常");
     }
    List<RedDetail> redDetailList =new ArrayList<RedDetail>(16);
     list.stream().forEach(s->{
         RedDetail redDetail=RedDetail.builder().redId(recordId)
                 .amount(Double.valueOf(s.toString()))
                 .createTime(new Date())
                 .isActive(1).build();
         redDetailList.add(redDetail);
     });
     this.saveBatch(redDetailList);
    }
}

