package com.zengzp.redcode.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 发红包记录表(RedRecord)表实体类
 *
 * @author zeengzp
 * @since 2023-03-19 16:32:43
 */
@SuppressWarnings("serial")
@Builder
@Data
public class RedRecord extends Model<RedRecord> {
    @TableId(type = IdType.AUTO)
    private Integer id;
    //用户ID
    private Integer userId;
    //人数
    private Integer total;
    //红包全局唯一标识
    private String redPacket;
    //总金额（单位分）
    private Double amount;
    //状态 1：有效 0：无效
    private Integer isActive;
    //创建时间
    private Date createTime;
    //更新时间
    private Date updateTime;

}

