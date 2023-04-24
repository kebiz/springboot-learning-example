package com.zengzp.redcode.entity;


import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;
import java.util.Date;

/**
 * 抢红包记录表(RedRodDetail)表实体类
 *
 * @author zeengzp
 * @since 2023-04-22 20:48:48
 */
@SuppressWarnings("serial")
public class RedRodDetail extends Model<RedRodDetail> {
    
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


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getRedPacket() {
        return redPacket;
    }

    public void setRedPacket(String redPacket) {
        this.redPacket = redPacket;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Date getRodTime() {
        return rodTime;
    }

    public void setRodTime(Date rodTime) {
        this.rodTime = rodTime;
    }

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取主键值
     *
     * @return 主键值
     */
    @Override
    protected Serializable pkVal() {
        return this.id;
    }
    }

