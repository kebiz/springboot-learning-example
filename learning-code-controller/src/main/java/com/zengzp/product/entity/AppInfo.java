package com.zengzp.product.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ：zengzhipeng
 * @date ：Created in 2022/11/10 15:15
 * @description：appInfo
 * @modified By：
 * @version: 1$
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppInfo {
    /** App id */
    private String appId;
    /** API 秘钥 */
    private String key;
}
