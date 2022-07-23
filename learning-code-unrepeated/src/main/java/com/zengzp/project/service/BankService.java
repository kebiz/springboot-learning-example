package com.zengzp.project.service;

import com.zengzp.project.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

/**
 * @author ：zengzhipeng
 * @date ：Created in 2022/7/13 11:33
 * @description：银行接口
 * @modified By：
 * @version: 1$
 */
public interface BankService {
    /**
     * 注册用户
     * @param name
     * @param identity
     * @param mobile
     * @param age
     * @return
     * @throws IOException
     */
      String createUser(String name, String identity, String mobile, int age) throws IOException;
      String pay(long userId, BigDecimal amount) throws IOException ;
      String remoteCall(AbstractAPI api);

}
