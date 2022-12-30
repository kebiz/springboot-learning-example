package com.zengzp.product.service.impl;

import cn.hutool.core.lang.UUID;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.zengzp.product.dao.ProductInfoMapper;
import com.zengzp.product.entity.ProductInfo;
import com.zengzp.product.service.ProductInfoService;
import com.zengzp.product.service.UserInfoService;
import com.zengzp.product.vo.TaskError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

/**
 * @author ：zengzhipeng
 * @date ：Created in 2022/7/18 16:36
 * @description：商品Servcie实现类
 * @modified By：
 * @version: 1.0$
 */
@Service
@Slf4j
public class ProductInfoServiceImpl extends ServiceImpl<ProductInfoMapper, ProductInfo> implements ProductInfoService {

    @Autowired
    private DataSourceTransactionManager dataSourceTransactionManager;
    @Autowired
    private PlatformTransactionManager platformTransactionManager;

    @Autowired
    private TransactionDefinition transactionDefinition;
    List<TransactionStatus> transactionStatuses = Collections.synchronizedList(new ArrayList<TransactionStatus>());
    @Override
    @Retryable(value = Exception.class, maxAttempts = 3, backoff = @Backoff(delay = 2000, multiplier = 1.5))
    public void retrySubmit(int code) throws Exception {
        System.out.println("========开始调用方法======");
        int i = 0;
        if (i == 0) {
            throw new Exception("出现异常");
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = {Exception.class})
    public void updateStudentWithThreads() throws InterruptedException {
        long start = System.currentTimeMillis();
        List<ProductInfo> list = this.list();
        // 线程数量
        final Integer threadCount = 5;
        //每个线程处理的数据量
        final Integer dataPartionLength = (list.size() + threadCount - 1) / threadCount;
        // 创建多线程处理任务
        ExecutorService studentThreadPool = new ThreadPoolExecutor(threadCount, threadCount,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(1024), new ThreadFactoryBuilder()
                .setNameFormat("demo-pool-%d").build(), new ThreadPoolExecutor.AbortPolicy());
        CountDownLatch threadLatchs = new CountDownLatch(threadCount);
        CountDownLatch mainLatchs = new CountDownLatch(1);
        AtomicBoolean isError = new AtomicBoolean();
        for (int i = 0; i < threadCount; i++) {
            List<ProductInfo> productInfoList=list.stream().skip(i * dataPartionLength).limit(dataPartionLength).collect(Collectors.toList());
            studentThreadPool.execute(() -> {
                this.updateProduct(productInfoList, platformTransactionManager,threadLatchs,isError,mainLatchs);
            });
        }
        try {
            log.info("等待子线程完成");
            boolean await=threadLatchs.await(30, TimeUnit.SECONDS);
            if(!await){
                isError.set(true);
                log.info("等待子线程超时");
            }
        } catch (Throwable e) {
            log.error("线程执行出错",e);
            isError.set(true);
            e.printStackTrace();
        }

        mainLatchs.countDown();
        studentThreadPool.shutdown();
       log.info("主线程完成");


    }
    @Autowired
    private ProductInfoMapper productInfoMapper;

    @Override
    public void updateProduct(List<ProductInfo> productInfoList, PlatformTransactionManager TransactionManager, CountDownLatch threadLatchs, AtomicBoolean isError, CountDownLatch mainLatchs) {
        // 使用这种方式将事务状态都放在同一个事务里面
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        // 事物隔离级别，开启新事务，这样会比较安全些。
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        // 获得事务状态
        TransactionStatus status = TransactionManager.getTransaction(def);
        transactionStatuses.add(status);
        log.info("子线程：{},开始执行任务，线程大小：{}", Thread.currentThread().getName(), productInfoList.size());
        try {
            productInfoList.forEach(s -> {
                String newProdectName=null;
                if(s.getProductId() ==8L) {
                    newProdectName = s.getProductName() + "_" + UUID.randomUUID().toString();
                }else {
                    int rand = new Random().nextInt(10000000);
                    newProdectName = s.getProductName() + "_" + rand;
                }
                log.info("=====商品{}正在更新=======", s.getProductId());
                s.setProductName(newProdectName);
                productInfoMapper.updateProduct(s);
            });
        }catch (Exception ex){
            isError.set(true);
            ex.printStackTrace();
        }finally {
            threadLatchs.countDown();
        }

        try {
            //等待主线程
            log.info("子线程：{}等待主线程", Thread.currentThread().getName());
            mainLatchs.await();
        } catch (Exception ex) {
            isError.set(true);
        }

        if (isError.get()) {
            log.info("子线程：{}出现异常回滚", Thread.currentThread().getName());
            TransactionManager.rollback(status);
        } else {
            log.info("子线程：{}，正在提交", Thread.currentThread().getName());
            TransactionManager.commit(status);
        }


    }

    @Recover
    public void recover(Exception e, int code) {
        System.out.println("回调方法执行！！！！");
        //记日志到数据库 或者调用其余的方法
    }
}
