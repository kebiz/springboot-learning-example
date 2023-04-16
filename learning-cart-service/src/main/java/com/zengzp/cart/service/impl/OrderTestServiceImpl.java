package com.zengzp.cart.service.impl;

import cn.hutool.core.collection.CollectionUtil;


import cn.hutool.json.JSONUtil;
import com.learning.code.common.model.CreateOrderMessage;
import com.learning.code.common.model.Order;
import com.learning.code.common.model.OrderDto;
import com.learning.code.common.model.OrderItem;
import com.learning.code.common.model.OrderVO;
import com.learning.code.common.util.IdWorker;
import com.learning.dubbo.CartService;
import com.zengzp.cart.mq.Sender;
import com.zengzp.cart.contant.CacheKey;
import com.learning.dubbo.MessageSendLogService;
import com.zengzp.cart.service.OrderTestService;
import com.zengzp.cart.service.StockService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author ：zengzhipeng
 * @date ：Created in 2023/2/1 15:56
 * @description：订单service实现类
 * @modified By：
 * @version: 1$
 */
@Service
@Slf4j
public class OrderTestServiceImpl implements OrderTestService {
    @Autowired
    private StockService stockService;
    @Autowired
    private CartService cartService;
    @Autowired
    private IdWorker idWorker;
    @Autowired
    private RedisTemplate redisTemplate;
    @Reference(version = "1.1")
    private MessageSendLogService messageSendLogService;
    @Resource
    private Sender sender;
    /**
     * 新增订单
     *
     * @param order
     * @return
     */
    @Override
    public Map<String, Object> preCheckOrder(Order order) throws IOException {
        Map<String,Object> resultMap=new HashMap<>(1);
        //1、根据用户名查询选中购物车
        List<OrderItem> cartList = cartService.findCartList(order.getUserName()).stream()
                .filter(s -> (boolean) s.get("checked") == true)
                .map(t->(OrderItem)t.get("item"))
                .collect(Collectors.toList());
        if(CollectionUtil.isEmpty(cartList)){
            throw new RuntimeException("没有商品购买项!");
        }

       //生成订单ID
        order.setId(idWorker.nextId());
        //3、扣减库存
        long result = stockService.deductionStock(cartList,order.getId());
        if(result!=0){
            throw new RuntimeException("库存扣减失败!");
        }
        //3、保存订单主表信息
        OrderDto orderDto =new OrderDto();
        orderDto.setOrder(order);
        orderDto.setOrderItems(cartList);
        //检查通过发送创建订单的消息
        CreateOrderMessage message = new CreateOrderMessage();
        message.setOrderDto(orderDto);
        message.setOrderId(order.getId());
        message.setUserId(order.getUserName());
        try{
            sender.sendCreateOrderMessage(message);
        }catch (Exception ex){
            //记录错误日志信息
            messageSendLogService.saveMsgSendLog(order.getId().toString(), JSONUtil.toJsonPrettyStr(message),"-1");
            throw new RuntimeException("rabbit服务器出错!");
        }finally {
            resultMap.put("orderId",order.getId());
            return resultMap;
        }

    }

    @Override
    public OrderVO getOrderListByuserName(String userName) {
        OrderVO orderVO=(OrderVO)redisTemplate.boundHashOps(CacheKey.ORDER_LIST.toString()).get(userName);
        return orderVO;
    }


}
