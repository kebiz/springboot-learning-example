package com.zengzp.redcode.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 抢红包记录表(RedRodDetail)表实体类
 *
 * @author zeengzp
 * @since 2023-04-22 20:48:48
 */
@SuppressWarnings("serial")
@Data
@Builder
public class RedRodDetail extends Model<RedRodDetail> {
    @TableId(type = IdType.AUTO)
    private Integer id;
    //用户账号
    private Integer userId;
    //红包全局唯一标识
    private String redPacket;
    //红包金额（单位分）
    private Double amount;
    //抢到红包时间
    private Date rodTime;
    //状态 1：有效 0：无效
    private Integer isActive;
    //创建时间
    private Date createTime;
    //更新时间
    private Date updateTime;

}

