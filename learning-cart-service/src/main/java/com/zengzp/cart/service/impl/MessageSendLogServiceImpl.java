package com.zengzp.cart.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.learning.code.common.model.MessageSendLog;
import com.zengzp.cart.mapper.MessageSendLogMapper;
import com.learning.dubbo.MessageSendLogService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * @author ：zengzhipeng
 * @date ：Created in 2023/3/9 17:22
 * @description：消息日志实现类
 * @modified By：
 * @version: 1$
 */
@Service(version = "1.2")
@Slf4j
public class MessageSendLogServiceImpl extends ServiceImpl<MessageSendLogMapper,MessageSendLog> implements MessageSendLogService {

    /**
     * 保存发送日志信息
     *
     */
    @Override
    public void saveMsgSendLog(MessageSendLog messageSendLog) {
        messageSendLog.setRetryCount(0);
        messageSendLog.setCreateTime(new Date());
        this.saveOrUpdate(messageSendLog);
    }

    /**
     * 更新消息发送状态
     *
     * @param msgId
     * @param status
     */
    @Override
    public void updateMsgStatus(String msgId, String status) {
        Assert.notEmpty(msgId,"消息ID不能为空");
        QueryWrapper<MessageSendLog> queryWrapper = new QueryWrapper<>();
        MessageSendLog log = this.getOne(queryWrapper.lambda().eq(MessageSendLog::getMsgId, msgId));
        log.setSendStatus(status);
        log.setUpdateTime(new Date());
        this.saveOrUpdate(log);
    }

    /**
     * 更新消息发送状态
     *
     * @param msgId
     */
    @Override
    public void updateMsgRetryCount(String msgId) {
        QueryWrapper<MessageSendLog> queryWrapper = new QueryWrapper<>();
        MessageSendLog log = this.getOne(queryWrapper.lambda().eq(MessageSendLog::getMsgId, msgId));
        log.setRetryCount(log.getRetryCount()+1);
        this.saveOrUpdate(log);
    }

    /**
     * 查询消息发送失败的记录
     *
     * @return
     */
    @Override
    public List<MessageSendLog> listBySendStatus() {
        QueryWrapper<MessageSendLog> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(MessageSendLog::getSendStatus,"2").le(MessageSendLog::getRetryCount,3);
        List<MessageSendLog> messageSendLogs = this.list(queryWrapper);
        return CollectionUtil.isEmpty(messageSendLogs)?CollectionUtil.newArrayList():messageSendLogs;
    }

    /**
     * 根据msgId查询发送日志对象
     *
     * @param msgId
     */
    @Override
    public MessageSendLog getMsgSendLogByMsgId(String msgId) {
        QueryWrapper<MessageSendLog> queryWrapper = new QueryWrapper<>();
        MessageSendLog log = this.getOne(queryWrapper.lambda().eq(MessageSendLog::getMsgId, msgId));
        return log;
    }
}
