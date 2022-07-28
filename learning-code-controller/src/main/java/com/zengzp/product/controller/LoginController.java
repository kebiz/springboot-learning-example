package com.zengzp.product.controller;

import cn.hutool.core.lang.UUID;
import com.zengzp.product.constants.AppCode;
import com.zengzp.product.entity.UserInfo;
import com.zengzp.product.service.UserInfoService;
import com.zengzp.product.vo.ResultVo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    @GetMapping("/loginSuccess")
    @ResponseBody
    public String loginSuccess() {
        return "success";
    }
    @PostMapping("/userLogin")
    @ResponseBody
    public ResultVo toLogin(@RequestParam("userName") String userName, @RequestParam("passWord")String passWord){
        UsernamePasswordToken token =new UsernamePasswordToken(userName,passWord);
        Subject subject = SecurityUtils.getSubject();
        try {
            //这里直接使用shiro的登录方法
            subject.login(token);
            return ResultVo.success();
            //登录失败时，会catch到你的异常，异常通过全局处理
        } catch (UnknownAccountException e) {
           return ResultVo.fail("该账号不存在");
        } catch (IncorrectCredentialsException e) {
            return ResultVo.fail("密码错误，请重试");
        } catch (LockedAccountException e) {
            return ResultVo.fail("该账户已被锁定,请联系管理员");
        } catch (Exception e) {
            return ResultVo.fail("登录失败");
        }
    }
    @RequestMapping("/insertUser")
    @ResponseBody
    public ResultVo insertUser(UserInfo user){
        try {
            //将uuid设置为密码盐值
            String salt = UUID.randomUUID().toString().replaceAll("-","");
            SimpleHash simpleHash = new SimpleHash("MD5", user.getPassword(), salt, 1024);
            //添加用户信息
            user.setPassword(simpleHash.toHex());
            user.setSalt(salt);
            user.setState(0);
            user.setCreateTime(new Date());
            userInfoService.save(user);
            return ResultVo.success();
        }catch (Exception ex){
            return ResultVo.fail(AppCode.APP_ERROR);
        }
    }

    public static void main(String[] args) {
        String salt = UUID.randomUUID().toString().replaceAll("-","");
        System.out.println("salt=="+salt);
        SimpleHash simpleHash = new SimpleHash("MD5", "123456", salt, 1024);

        System.out.println("password=="+simpleHash.toHex());
    }
}
