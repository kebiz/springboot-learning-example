package com.zengzp.product.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * @TableName role_permission
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class RolePermission extends AbstractDO {
    /**
     * 权限表主键
     */
    private Integer permissionId;

    /**
     * 角色表主键
     */
    private Integer roleId;

    private static final long serialVersionUID = 1L;
}