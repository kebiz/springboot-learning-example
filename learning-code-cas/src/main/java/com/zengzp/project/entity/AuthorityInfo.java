package com.zengzp.project.entity;

import org.springframework.security.core.GrantedAuthority;

/**
 * @author ：zengzhipeng
 * @date ：Created in 2022/12/15 17:22
 * @description：认证信息表
 * @modified By：
 * @version: 1$
 */
public class AuthorityInfo implements GrantedAuthority {
    /**
     * 权限CODE
     */
    private String authority;

    public AuthorityInfo(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

}
