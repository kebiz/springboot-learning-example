package com.zengzp.product.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zengzp.product.dao.mapper.RoleMapper;
import com.zengzp.product.entity.Role;
import com.zengzp.product.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
*
*/
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role>
implements RoleService{
    @Autowired
    private RoleMapper roleMapper;
    @Override
    public List<Role> listRolesByLoginName(String loginName) {
        List<Role> roles = roleMapper.listRolesByLoginName(loginName);
        if(CollectionUtil.isEmpty(roles)) {
            return null;
        }
        return roles;
    }
}
