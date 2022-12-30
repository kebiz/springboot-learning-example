package com.zengzp.project.controller;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @Value("${cas.server.host.url}")
    private String casServerUrl;

    @Value("${cas.server.host.login_url}")
    private String casServerLoginUrl;
    @GetMapping
    public String hello() {
        return "hello, user"+casServerUrl+casServerLoginUrl;
    }

}
