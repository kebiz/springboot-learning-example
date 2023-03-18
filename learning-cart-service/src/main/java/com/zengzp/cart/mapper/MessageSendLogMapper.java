package com.zengzp.cart.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.learning.code.common.model.MessageSendLog;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface MessageSendLogMapper extends BaseMapper<MessageSendLog> {
}
