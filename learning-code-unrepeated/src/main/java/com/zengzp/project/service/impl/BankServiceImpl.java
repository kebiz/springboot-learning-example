package com.zengzp.project.service.impl;

import com.zengzp.project.model.*;
import com.zengzp.project.service.BankService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Comparator;

/**
 * @author ：zengzhipeng
 * @date ：Created in 2022/7/15 9:09
 * @description：银行业务实现类
 * @modified By：
 * @version: 1$
 */
@Service
public class BankServiceImpl implements BankService{
    @Autowired
    RestTemplate restTemplate;
    private static  final Logger log= LoggerFactory.getLogger(BankServiceImpl.class);
    /**
     * 注册用户
     * @param name
     * @param identity
     * @param mobile
     * @param age
     * @return
     * @throws IOException
     */
    @Override
    public  String createUser(String name, String identity, String mobile, int age) throws IOException {
        CreateUserAPI createUserAPI = new CreateUserAPI();
        createUserAPI.setName(name);
        createUserAPI.setIdentity(identity);
        createUserAPI.setAge(age);
        createUserAPI.setMobile(mobile);
        return remoteCall(createUserAPI);
    }
    @Override
    public  String pay(long userId, BigDecimal amount) throws IOException {
        PayAPI payAPI = new PayAPI();
        payAPI.setUserId(userId);
        payAPI.setAmount(amount);
        return remoteCall(payAPI);
    }
    @Override
    public String remoteCall(AbstractAPI api){
        BankAPI bankAPI=api.getClass().getAnnotation(BankAPI.class);
        String url=bankAPI.url();
        StringBuilder stringBuilder = new StringBuilder();
        Arrays.stream(api.getClass().getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(BankAPIFeild.class))
                .sorted(Comparator.comparing(a -> a.getAnnotation(BankAPIFeild.class).order()))
                .peek(field -> field.setAccessible(true))
                .forEach(field -> {
                    BankAPIFeild bankAPIFeild=field.getAnnotation(BankAPIFeild.class);
                    Object value=null;
                    try {
                        value=field.get(api);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    switch (bankAPIFeild.type()){
                        case "S": {
                            stringBuilder.append(String.format("%-" + bankAPIFeild.length() + "s", value.toString()).replace(' ', '_'));
                            break;
                        }
                        case  "N": {
                            stringBuilder.append(String.format("%" + bankAPIFeild.length() + "s", value.toString()).replace(' ', '0'));
                            break;
                        }
                        case  "M": {
                            if (!(value instanceof BigDecimal)) {
                                throw new RuntimeException(String.format("{} 的 {} 必须是BigDecimal", api, field));
                            }
                            stringBuilder.append(String.format("%0" + bankAPIFeild.length() + "d", ((BigDecimal) value).setScale(2, RoundingMode.DOWN).multiply(new BigDecimal("100")).longValue()));
                            break;
                        }
                        default:
                            break;
                    }
                });
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Content-Type", "application/x-www-form-urlencoded");
        //设置请求参数
        MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
        param.add("param",stringBuilder.toString());
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(param, requestHeaders);
        long begin=System.currentTimeMillis();
        String result=restTemplate.postForObject(url,requestEntity,String.class);
        log.info("调用银行API {} url:{} 参数:{} 耗时:{}ms", bankAPI.desc(), bankAPI.url(), param, System.currentTimeMillis() - begin);
        return result;

    }
}
