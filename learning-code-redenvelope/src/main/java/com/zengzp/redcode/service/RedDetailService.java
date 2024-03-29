package com.zengzp.redcode.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zengzp.redcode.dto.RedPacketDto;
import com.zengzp.redcode.entity.RedDetail;

import java.util.List;

/**
 * 红包明细金额(RedDetail)表服务接口
 *
 * @author zeengzp
 * @since 2023-04-22 20:38:48
 */
public interface RedDetailService extends IService<RedDetail> {
    /**
     * 记录发红包明细
     * @param recordId
     * @param list
     */
    void recordRedPacket(Integer recordId, List<Integer> list)throws Exception;

}

