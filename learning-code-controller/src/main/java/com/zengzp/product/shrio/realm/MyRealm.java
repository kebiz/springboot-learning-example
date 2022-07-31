package com.zengzp.product.shrio.realm;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zengzp.product.entity.Permission;
import com.zengzp.product.entity.ProductInfo;
import com.zengzp.product.entity.Role;
import com.zengzp.product.entity.UserInfo;
import com.zengzp.product.service.PermissionService;
import com.zengzp.product.service.RoleService;
import com.zengzp.product.service.UserInfoService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author ：zengzhipeng
 * @date ：Created in 2022/7/27 16:34
 * @description：自定义realm类
 * @modified By：
 * @version: 1.0$
 */
public class MyRealm extends AuthorizingRealm {
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PermissionService permissionService;
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        UserInfo userInfo = (UserInfo) principalCollection.getPrimaryPrincipal();
        //判断数据库是否有这用户
        if (userInfo == null) {
            throw new UnknownAccountException();
        }
        //添加用户角色
        List<Role> roleList=roleService.listRolesByLoginName(userInfo.getLoginName());
        if(CollectionUtil.isNotEmpty(roleList)){
            for(Role role:roleList){
                simpleAuthorizationInfo.addRole(role.getRole());
            }
        }
        //添加角色权限
        List<Permission> permissions = permissionService.listByLoginName(userInfo.getLoginName());
        if (!CollectionUtils.isEmpty(permissions)) {
            Set<String> permissionSet = new HashSet<>();
            for (Permission p : permissions) {
                if (!StringUtils.isEmpty(p.getPermission())) {
                    permissionSet.addAll(Arrays.asList(p.getPermission().trim().split(",")));
                }
            }
            simpleAuthorizationInfo.setStringPermissions(permissionSet);
        }
        return simpleAuthorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
       UsernamePasswordToken usernamePasswordToken=(UsernamePasswordToken) authenticationToken;
       String loginName=usernamePasswordToken.getUsername();
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(UserInfo::getLoginName,loginName);
        UserInfo user=userInfoService.getOne(queryWrapper);
        //判断数据库是否有这用户
        if (user == null) {
            throw new UnknownAccountException();
        }
        //判断用户的状态是否被禁用(数据库的字段)
        if (user.getState() == 0) {
            throw new LockedAccountException();
        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                user,
                user.getPassword(),
                //这里是设置的密码盐
                ByteSource.Util.bytes(user.getSalt()),
                getName());
        return authenticationInfo;
    }
}
