package com.zengzp.project.controller;

import com.zengzp.project.DTO.CreateUserDTO;
import com.zengzp.project.model.Car;
import com.zengzp.project.model.CreateUserAPI;
import com.zengzp.project.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpRequest;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sun.misc.Request;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ：zengzhipeng
 * @date ：Created in 2022/7/12 15:19
 * @description：购物车控制类
 * @modified By：
 * @version: 1$
 */
@RestController
@RequestMapping("/cart")
public class UserCartController {
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private BankService bankService;
    @RequestMapping("/findCartList")
    public Car findCartList(@RequestParam("userId") long userId) {
     //根据用户ID查询用户类型
        //这里省略
        String catagory="vip";
        Map<Long, Integer> item = new HashMap();
        item.put(1L,30);
        item.put(2L,40);
        item.put(4L,50);
        AbstractUserApi abstractUserApi =(AbstractUserApi) applicationContext.getBean(catagory+"UserService");
        return abstractUserApi.process(userId,item);
    }
    @RequestMapping("/createUser")
    public String creareUser(HttpServletRequest httpServletRequest) {
        //根据用户ID查询用户类型
        //这里省略
        String param= httpServletRequest.getParameter("param");
        System.out.println("param======="+param);
        String user="{id:1,name:张三}";

        return user;
    }

    @RequestMapping("/bank")
    public String bank() throws IOException {
        //根据用户ID查询用户类型
        //这里省略
        CreateUserDTO dto=new CreateUserDTO();
        dto.setAge(34);
        dto.setId(1);
        dto.setIdentity("1235");
        dto.setMobile("1234567");
        dto.setName("王五");
        dto.setAddress("qingfen");
        CreateUserAPI api=new CreateUserAPI();
        BeanUtils.copyProperties(dto,api,"id","address");
        return  api.toString();
        //return bankService.createUser("张三","43025155458987452","1254545",8);

    }
}
