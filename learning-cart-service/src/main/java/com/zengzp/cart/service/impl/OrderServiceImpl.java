package com.zengzp.cart.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.zengzp.cart.contant.CacheKey;
import com.zengzp.cart.entity.Order;
import com.zengzp.cart.entity.OrderItem;
import com.zengzp.cart.entity.OrderVO;
import com.zengzp.cart.entity.Orders;
import com.zengzp.cart.service.CartService;
import com.zengzp.cart.service.OrderService;
import com.zengzp.cart.service.StockService;
import com.zengzp.cart.util.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author ：zengzhipeng
 * @date ：Created in 2023/2/1 15:56
 * @description：订单service实现类
 * @modified By：
 * @version: 1$
 */
public class OrderServiceImpl implements OrderService {
    @Autowired
    private StockService stockService;
    @Autowired
    private CartService cartService;
    @Resource
    private IdWorker idWorker;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 新增订单
     *
     * @param order
     * @return
     */
    @Override
    public Map<String, Object> add(Order order) {
        Map<String,Object> resultMap=new HashMap<>(1);
        //1、根据用户名查询选中购物车
        List<OrderItem> cartList = cartService.findCartList(order.getUserName()).stream()
                .filter(s -> (boolean) s.get("checked") == true)
                .map(t->(OrderItem)t.get("item"))
                .collect(Collectors.toList());
        //2、扣减库存
        boolean isSuccess = stockService.deductionStock(cartList);
        if(!isSuccess){
            throw new RuntimeException("库存不足!");
        }
            //3、保存订单主表信息
            //从缓存中获取当前用户的订单信息
           OrderVO orderRedis =(OrderVO)redisTemplate.boundHashOps(CacheKey.ORDER_LIST.toString()).get(order.getUserName());
            Orders orders=new Orders();
            List<Orders> ordersList;
           if(orderRedis==null){
               orderRedis=new OrderVO();
               orderRedis.setUserName(order.getUserName());
                ordersList=new ArrayList<>();
           }else {
               ordersList=orderRedis.getOrdersList();
           }

            List<OrderItem> orderItemList=new ArrayList<>();
            order.setId(idWorker.nextId());
            order.setTotalMoney(cartList.stream().mapToDouble(OrderItem::getPrice).sum());
            order.setOrderStatus("0");
            order.setCreateTime(new Date());
            order.setPreMoney(cartService.reduceCartPreMoney(order.getUserName()));
            order.setPayStatus("0");
            order.setTotalNum(cartList.stream().mapToInt(OrderItem::getNum).sum());
            order.setUserName(order.getUserName());
            BigDecimal payMoney=new BigDecimal(order.getTotalMoney()).subtract(new BigDecimal(order.getPreMoney()));
            order.setPayMoney(payMoney.doubleValue());
            orders.setOrder(order);
            //4、保存订单详情表
            BigDecimal propMoney=new BigDecimal(order.getPayMoney()).divide(new BigDecimal(order.getTotalMoney()));
            for (OrderItem orderItem:cartList){
                orderItem.setId(idWorker.nextId());
                orderItem.setOrderId(order.getId()+"");
                BigDecimal pMoney=new BigDecimal(orderItem.getTotalMoney()).multiply(propMoney);
                orderItem.setPayMoney(pMoney.doubleValue());
                orderItemList.add(orderItem);
            }
            orders.setOrderItems(orderItemList);
            ordersList.add(orders);
            orderRedis.setOrdersList(ordersList);
            redisTemplate.boundHashOps(CacheKey.ORDER_LIST.toString()).put(order.getUserName(),orderRedis);
        //5、清除购物车信息
            cartService.deleteCartItem(order.getUserName());
        resultMap.put("orderId",order.getId());
        return resultMap;
    }
}
