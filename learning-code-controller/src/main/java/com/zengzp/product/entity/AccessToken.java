package com.zengzp.product.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

/**
 * @author ：zengzhipeng
 * @date ：Created in 2022/11/10 15:05
 * @description：accessToken
 * @modified By：
 * @version: 1$
 */
@Data
@AllArgsConstructor
public class AccessToken {
    /** token */
    private String token;

    /** 失效时间 */
    private Date expireTime;
}
