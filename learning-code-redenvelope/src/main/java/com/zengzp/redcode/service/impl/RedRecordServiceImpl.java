package com.zengzp.redcode.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.learning.code.common.util.IdWorker;
import com.zengzp.redcode.dao.RedRecordDao;
import com.zengzp.redcode.dao.RedRodDetailDao;
import com.zengzp.redcode.dto.RedPacketDto;
import com.zengzp.redcode.entity.RedDetail;
import com.zengzp.redcode.entity.RedRecord;
import com.zengzp.redcode.entity.RedRodDetail;
import com.zengzp.redcode.service.RedDetailService;
import com.zengzp.redcode.service.RedRecordService;
import com.zengzp.redcode.util.RedPacketUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * 发红包记录表(RedRecord)表服务实现类
 *
 * @author zeengzp
 * @since 2023-03-19 16:32:44
 */
@Service("redRecordService")
@Slf4j
public class RedRecordServiceImpl extends ServiceImpl<RedRecordDao, RedRecord> implements RedRecordService {
    @Resource
    private IdWorker idWorker;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private RedRecordDao redRecordDao;
    @Autowired
    private RedRodDetailDao redRodDetailDao;
    @Autowired
    private RedDetailService redDetailService;
    private static  final String redKey=":total:key";
    /**
     * 抢红包逻辑
     *
     * @param userId
     * @param redId
     * @return
     * @throws Exception
     */
    @Override
    public BigDecimal rod(Integer userId, String redId) throws Exception {
        Object object=redisTemplate.opsForValue().get(userId+redId+":rod");
        //判断用户是否已经抢过红包
        String redTotal_key=redId+redKey;
        if(object!=null){
            return new BigDecimal(object.toString());
        }
        //判断是否还有红包
        Integer total=(Integer)redisTemplate.opsForValue().get(redTotal_key);
        if(total==null || total==0){
            throw new Exception("红包已抢完");
        }
        Integer red=(Integer) redisTemplate.opsForList().rightPop(redId);
        if(red==null){
            throw new Exception("红包已抢完");
        }
        //红包个数减一
        redisTemplate.opsForValue().increment(redTotal_key,-1);
        //记录抢红包明细
        RedRodDetail redRodDetail=RedRodDetail.builder().amount(new BigDecimal(red.toString()).doubleValue())
                .createTime(new Date())
                .isActive(1)
                .redPacket(redId)
                .rodTime(new Date())
                .userId(userId).build();
        redRodDetailDao.insert(redRodDetail);
       //记录用户抢过红包
        redisTemplate.opsForValue().set(userId+redId+":rod",red);
        return new BigDecimal(red.toString());
    }

    /**
     * 发红包逻辑
     *
     * @param redPacketDto
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String handOut(RedPacketDto redPacketDto) throws Exception {
        if(redPacketDto.getAmount()>0 && redPacketDto.getTotal()>0){
            Integer amount=redPacketDto.getAmount();
            Integer total=redPacketDto.getTotal();
            String redId=idWorker.nextId()+"";
            String redTotal_key=redId+redKey;
            //根据二倍分值算法生成红包金额
            List<Integer> reds = RedPacketUtil.divideRedPacket(amount, total);
            //生成的红包存入缓存
            redisTemplate.opsForList().leftPushAll(redId,reds);
            //记录红包个数
            redisTemplate.opsForValue().set(redTotal_key,total);
            //生成的红包金额存入发红包记录表
            RedRecord redRecord = RedRecord.builder().amount(BigDecimal.valueOf(amount).doubleValue())
                    .createTime(new Date())
                    .isActive(1)
                    .redPacket(redId)
                    .total(total)
                    .userId(redPacketDto.getUserId()).build();
            redRecordDao.insert(redRecord);
            //异步存入红包明细表
            redDetailService.recordRedPacket(redRecord.getId(),reds);
            return redId;

        }else {
            throw new  Exception("系统异常：参数错误");
        }
    }
}

