package com.zengzp.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zengzp.product.entity.Role;

import java.util.List;

/**
*
*/
public interface RoleService extends IService<Role> {
    List<Role> listRolesByLoginName(String loginName);
}
