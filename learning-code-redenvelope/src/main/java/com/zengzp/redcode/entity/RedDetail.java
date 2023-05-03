package com.zengzp.redcode.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 红包明细金额(RedDetail)表实体类
 *
 * @author zeengzp
 * @since 2023-04-22 20:38:42
 */
@SuppressWarnings("serial")
@Builder
@Data
public class RedDetail extends Model<RedDetail> {
    @TableId(type = IdType.AUTO)
    private Integer id;
    //红包记录ID
    private Integer redId;
    //每个红包金额（单位分）
    private Double amount;
    //状态 1：有效 0：无效
    private Integer isActive;
    //创建时间
    private Date createTime;
    //更新时间
    private Date updateTime;
}

