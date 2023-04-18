package com.learning.dubbo;


import com.learning.code.common.model.MessageSendLog;

import java.util.List;

public interface MessageSendLogService  {
    /**
     * 保存发送日志信息
     */
    void saveMsgSendLog(MessageSendLog messageSendLog);
    /**
     * 更新消息发送状态
     */
    void updateMsgStatus(String msgId,String status);
    /**
     * 更新消息发送状态
     */
    void updateMsgRetryCount(String msgId);
    /**
     * 查询消息发送失败的记录
     * @return
     */
    List<MessageSendLog> listBySendStatus();
}
