package com.zengzp.product.controller;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.zengzp.product.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.util.concurrent.*;

/**
 * @author ：zengzhipeng
 * @date ：Created in 2022/8/17 11:37
 * @description：扣减库存
 * @modified By：
 * @version: 1.0$
 */
@RestController
public class StockController {
    @Autowired
    private StockService stockService;

    @GetMapping("/stock")
    public Object stock()  {
        long start=System.currentTimeMillis();
        // 创建多线程处理任务
        ExecutorService threadPool = new ThreadPoolExecutor(1000, 1000,
                5L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(1024), new ThreadFactoryBuilder()
                .setNameFormat("demo-pool-%d").build(), new ThreadPoolExecutor.DiscardPolicy());
        CountDownLatch threadLatchs = new CountDownLatch(10000);

        // 商品ID
        long commodityId = 9;
        // 库存ID
        Random random=new Random();
        String redisKey = "redis_key:stock:" + commodityId;

        for (int i=0;i<10000;i++){
            threadPool.execute(()->{
                int rand=random.nextInt(5);
                System.out.println("线程："+Thread.currentThread().getName()+"：下单了="+rand);
                long  stock=stockService.stock(redisKey,60 * 60,rand);
                if(stock >=0){
                    System.out.println("下单成功！购买了"+rand+"个商品");
                }else if(stock ==-2){
                    System.out.println("下单失败！商品已售完！下单了"+rand+"个商品");
                }else if(stock ==-3){
                    System.out.println("下单失败！商品未初始化!");
                }else {
                    System.out.println("下单失败！商品未初始化!");
                }
                threadLatchs.countDown();
            });
        }
        try {
            threadLatchs.await();
            threadPool.shutdown();
        }catch (Exception ex){
            ex.printStackTrace();
        }

        long time=System.currentTimeMillis()-start;
        System.out.println("共耗时："+time);
        /*long stock = stockService.stock(redisKey, 60 * 60, 2, () -> initStock(commodityId));*/

        return 0;
    }

    /**
     * 获取初始的库存
     *
     * @return
     */
    /*private int initStock(long commodityId) {
        // TODO 这里做一些初始化库存的操作
        System.out.println("初始化库存1000");
        return 1000;
    }*/

    @GetMapping("/getStock")
    public Object getStock() {
        // 商品ID
        long commodityId = 9;
        // 库存ID
        String redisKey = "redis_key:stock:" + commodityId;

        return stockService.getStock(redisKey);
    }

    @GetMapping("/addStock")
    public Object addStock() {
        // 商品ID
        long commodityId = 1;
        // 库存ID
        String redisKey = "redis_key:stock:" + commodityId;

        return stockService.addStock(redisKey, 2);
    }
}
