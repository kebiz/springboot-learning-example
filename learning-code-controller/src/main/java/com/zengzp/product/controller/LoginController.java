package com.zengzp.product.controller;

import cn.hutool.core.lang.UUID;
import com.zengzp.product.constants.AppCode;
import com.zengzp.product.entity.UserInfo;
import com.zengzp.product.service.UserInfoService;
import com.zengzp.product.vo.ResultVo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

/**
 * @author ：zengzhipeng
 * @date ：Created in 2022/7/28 11:12
 * @description：登录控制类
 * @modified By：
 * @version: 1.0$
 */
@Controller
//@RequestMapping("/passport")
public class LoginController {
    /**
    * 登录页面 这里对应shiro中配置的shiroFilter的登录方法
    * @return
     */
    @Autowired
    private UserInfoService userInfoService;
    @GetMapping("/login")
    public ModelAndView login() {
        return ResultVo.view("/login");
    }
    @PostMapping("/editUser")
    @ResponseBody
    @RequiresPermissions("user:edit")
    public ResultVo editUser() {
        return ResultVo.success();
    }
    @PostMapping("/userLogin")
    @ResponseBody
    public ResultVo toLogin(@RequestParam("userName") String userName, @RequestParam("passWord")String passWord){
        UsernamePasswordToken token =new UsernamePasswordToken(userName,passWord);
        Subject subject = SecurityUtils.getSubject();
            //这里直接使用shiro的登录方法
            subject.login(token);
            return ResultVo.success();
    }
    @RequestMapping("/insertUser")
    @ResponseBody
    @RequiresPermissions("user:add")
    public ResultVo insertUser(UserInfo user){
        try {
            //将uuid设置为密码盐值
            String salt = UUID.randomUUID().toString().replaceAll("-","");
            SimpleHash simpleHash = new SimpleHash("MD5", user.getPassword(), salt, 1024);
            //添加用户信息
            user.setPassword(simpleHash.toHex());
            user.setSalt(salt);
            user.setState(1);
            user.setCreateTime(new Date());
            userInfoService.save(user);
            return ResultVo.success();
        }catch (Exception ex){
            return ResultVo.fail(AppCode.APP_ERROR);
        }
    }
    @RequestMapping("/delUser")
    @RequiresPermissions("user:del")
    public ResultVo delUser(@RequestParam("userId") long userId){
        return ResultVo.success();
    }
}
