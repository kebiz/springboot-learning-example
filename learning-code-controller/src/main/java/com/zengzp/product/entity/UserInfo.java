package com.zengzp.product.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * @TableName user_info
 */
@TableName(value ="user_info")
@Data
@EqualsAndHashCode(callSuper = false)
public class UserInfo extends AbstractDO {

    /**
     * 
     */
    private String loginName;

    /**
     * 
     */
    private String password;

    /**
     * 
     */
    private String salt;

    /**
     * 
     */
    private Integer state;

    /**
     * 
     */
    private String username;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}