package com.zengzp.redcode.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zengzp.redcode.dto.RedPacketDto;
import com.zengzp.redcode.entity.RedRecord;

import java.math.BigDecimal;

/**
 * 发红包记录表(RedRecord)表服务接口
 *
 * @author zengzp
 * @since 2023-03-19 16:32:44
 */
public interface RedRecordService extends IService<RedRecord> {
     /**
      * 发红包逻辑
      * @param redPacketDto
      * @return
      */
     String handOut(RedPacketDto redPacketDto)throws Exception ;

     /**
      * 抢红包逻辑
      * @param userId
      * @param redId
      * @return
      * @throws Exception
      */
     BigDecimal rod(Integer userId,String redId)throws  Exception;
}

