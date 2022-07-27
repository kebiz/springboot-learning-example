package com.zengzp.product.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName permission
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Permission extends AbstractDO {

    /**
     * 
     */
    private String name;

    /**
     * 
     */
    private String permission;

    /**
     * 
     */
    private String url;

}