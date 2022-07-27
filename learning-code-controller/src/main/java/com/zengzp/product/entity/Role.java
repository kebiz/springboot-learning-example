package com.zengzp.product.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * @TableName role
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Role extends AbstractDO {
    /**
     * 
     */
    private String available;

    /**
     * 
     */
    private String description;

    /**
     * 
     */
    private String role;


    private static final long serialVersionUID = 1L;
}