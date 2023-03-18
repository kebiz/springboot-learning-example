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

import java.util.List;

/**
 * @author ：zengzhipeng
 * @date ：Created in 2023/3/9 17:22
 * @description：消息日志实现类
 * @modified By：
 * @version: 1$
 */
@Service
@Slf4j
public class MessageSendLogServiceImpl extends ServiceImpl<MessageSendLogMapper,MessageSendLog> implements MessageSendLogService {

    /**
     * 保存发送日志信息
     *
     * @param msgId
     * @param msgContent
     * @param sendStatus
     */
    @Override
    public void saveMsgSendLog(Long msgId, String msgContent, String sendStatus) {
        Assert.notEmpty(new Long[]{msgId},"业务ID不能为空");
        Assert.notEmpty(msgContent,"消息内容不能为空");
        Assert.notEmpty(sendStatus,"发送状态不能为空");
        QueryWrapper<MessageSendLog> queryWrapper = new QueryWrapper<>();
        MessageSendLog messageSendLog = this.getOne(queryWrapper.lambda().eq(MessageSendLog::getMsgId, msgId));
        if(messageSendLog==null){
            messageSendLog=new MessageSendLog();
            messageSendLog.setRetryCount(0);
        }else {
            messageSendLog.setRetryCount(messageSendLog.getRetryCount()+1);
        }
        messageSendLog.setMsgId(msgId);
        messageSendLog.setMsgContent(msgContent);
        messageSendLog.setSendStatus(sendStatus);
        this.saveOrUpdate(messageSendLog);
    }

    /**
     * 查询消息发送失败的记录
     *
     * @return
     */
    @Override
    public List<MessageSendLog> listBySendStatus() {
        QueryWrapper<MessageSendLog> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(MessageSendLog::getSendStatus,"1").le(MessageSendLog::getRetryCount,2);
        List<MessageSendLog> messageSendLogs = this.list(queryWrapper);
        return CollectionUtil.isEmpty(messageSendLogs)?CollectionUtil.newArrayList():messageSendLogs;
    }
}
