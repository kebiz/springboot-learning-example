package com.zengzp.project.entity;

import lombok.Data;

/**
 * @author ：zengzhipeng
 * @date ：Created in 2022/11/24 11:56
 * @description：用户测试类
 * @modified By：
 * @version: 1$
 */
@Data
public class UserTest {
    private Long id;
    private String name;
    private Long age;
    private String sex;
    private  String remark;
    private  String[] tags;
}
