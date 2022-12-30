package com.zengzp.product.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * @TableName user_info
 */
@TableName(value ="user_info")
@Data
@EqualsAndHashCode(callSuper = false)
public class UserToken extends AbstractDO {

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

    private AccessToken accessToken;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    public UserToken(String loginName, String password, String salt){
        this.loginName=loginName;
        this.password=password;
        this.salt=salt;
    }
}