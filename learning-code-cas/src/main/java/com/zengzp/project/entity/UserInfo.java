package com.zengzp.project.entity;

import lombok.Data;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.HashSet;
import java.util.Set;

/**
 * @author ：zengzhipeng
 * @date ：Created in 2022/12/15 17:22
 * @description：登录用户
 * @modified By：
 * @version: 1$
 */
@Data
public class UserInfo implements UserDetails {
    /**
     * 用户ID
     */
    private Long id;

    /**
     * 用户名称
     */
    private String name;

    /**
     * 登录名称
     */
    private String username;

    /**
     * 登录密码
     */
    private String password;

    private boolean isAccountNonExpired = true;

    private boolean isAccountNonLocked = true;

    private boolean isCredentialsNonExpired = true;

    private boolean isEnabled = true;

    private Set<AuthorityInfo> authorities = new HashSet<AuthorityInfo>();
}
