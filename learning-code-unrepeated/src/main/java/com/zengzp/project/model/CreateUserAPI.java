package com.zengzp.project.model;

import lombok.Data;

/**
 * @author ：zengzhipeng
 * @date ：Created in 2022/7/14 10:46
 * @description：注册用户实体
 * @modified By：
 * @version: 1$
 */
@BankAPI(url = "http://localhost:8081/learning/cart/createUser",desc = "银行接口")
@Data
public class CreateUserAPI extends AbstractAPI{
    @BankAPIFeild(order = 1,length = 10,type = "S")
    private String name;
    @BankAPIFeild(order = 2,length = 18,type = "S")
    private String identity;
    @BankAPIFeild(order = 3,length = 11,type = "S")
    private String mobile;
    @BankAPIFeild(order = 4,length = 5,type = "N")
    private int age;
}
