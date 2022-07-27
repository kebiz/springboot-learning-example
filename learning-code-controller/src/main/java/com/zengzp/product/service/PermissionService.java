package com.zengzp.product.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.zengzp.product.entity.Permission;

import java.util.List;

/**
*
*/
public interface PermissionService extends IService<Permission> {
    /**
     * 根据登录名获取角色权限信息
     * @param loginName
     * @return
     */
    List<Permission> listByLoginName(String loginName);
}
