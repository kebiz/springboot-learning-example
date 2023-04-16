package com.learning.dubbo;


import com.learning.code.common.model.MessageSendLog;

import java.util.List;

public interface MessageSendLogService  {
    /**
     * 保存发送日志信息
     * @param msgId
     * @param msgContent
     * @param sendStatus
     */
    void saveMsgSendLog(String msgId,String msgContent,String sendStatus);

    /**
     * 查询消息发送失败的记录
     * @return
     */
    List<MessageSendLog> listBySendStatus();
}
