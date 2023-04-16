package com.learning.code.common.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author ：zengzhipeng
 * @date ：Created in 2023/3/9 16:50
 * @description：消息发送日志
 * @modified By：
 * @version: 1$
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_message_send_log")
public class MessageSendLog implements Serializable {
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    /**
     * 业务ID
     */
    private String msgId;
    /**
     *消息内容
     */
    private String msgContent;
    /**
     * 发送状态
     */
    private String sendStatus;
    /**
     * 重试次数
     */
    private int retryCount;
    /**
     * 创建时间
     */
    private Date createTime;


}
