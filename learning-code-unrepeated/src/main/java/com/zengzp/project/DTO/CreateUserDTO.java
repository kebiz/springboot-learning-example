package com.zengzp.project.DTO;

import com.zengzp.project.model.BankAPIFeild;
import lombok.Data;

/**
 * @author ：zengzhipeng
 * @date ：Created in 2022/7/15 9:51
 * @description：用户DTO对象
 * @modified By：
 * @version: 1.0$
 */
@Data
public class CreateUserDTO {
    private String name;

    private String identity;

    private String mobile;

    private int age;

    private long id;

    private String address;
}
