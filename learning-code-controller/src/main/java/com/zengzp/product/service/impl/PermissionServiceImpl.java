package com.zengzp.product.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zengzp.product.dao.mapper.PermissionMapper;
import com.zengzp.product.entity.Permission;
import com.zengzp.product.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
*
*/
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission>
implements PermissionService {
    @Autowired
    private PermissionMapper permissionMapper;
    @Override
    public List<Permission> listByLoginName(String loginName) {
        List<Permission> permissions = permissionMapper.listByLoginName(loginName);
        if(CollectionUtil.isEmpty(permissions)) {
            return null;
        }
        return permissionMapper.listByLoginName(loginName);
    }
}
