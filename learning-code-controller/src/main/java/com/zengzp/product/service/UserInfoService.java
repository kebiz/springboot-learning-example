package com.zengzp.product.service;

import com.zengzp.product.entity.ProductInfo;
import com.zengzp.product.entity.UserInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 *
 */
public interface UserInfoService extends IService<UserInfo> {
}
