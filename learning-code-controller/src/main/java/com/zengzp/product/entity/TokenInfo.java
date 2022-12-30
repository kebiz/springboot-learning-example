package com.zengzp.product.entity;

import lombok.Data;

/**
 * @author ：zengzhipeng
 * @date ：Created in 2022/11/10 15:25
 * @description：TokenInfo
 * @modified By：
 * @version: 1$
 */
@Data
public class TokenInfo {
    /** token类型: api:0 、user:1 */
    private Integer tokenType;

    /** App 信息 */
    private AppInfo appInfo;

    /** 用户其他数据 */
    private UserToken userInfo;
}
