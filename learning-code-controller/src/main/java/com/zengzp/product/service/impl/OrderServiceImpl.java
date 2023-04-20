package com.zengzp.product.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.learning.code.common.model.*;
import com.learning.code.common.util.IdWorker;
import com.learning.dubbo.CartService;
import com.learning.dubbo.MessageSendLogService;
import com.zengzp.product.dao.mapper.OrderMapper;
import com.zengzp.product.mq.Sender;
import com.zengzp.product.service.OrderItemService;
import com.zengzp.product.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.redisson.api.RScript;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.StringCodec;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 *
 */
@Service
@Slf4j
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order>
        implements OrderService {
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private IdWorker idWorker;
    @Autowired
    private Sender sender;
    @Reference(version = "1.0.0")
    private CartService cartService;
    @Reference(version = "1.2")
    private MessageSendLogService messageSendLogService;
    @Autowired
    private RedissonClient redissonClient;
    /**
     * 创建订单
     *
     * @param dto
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean createOrder(OrderDto dto) {
        Boolean isSuc=true;
        try {
            if(dto==null){
                throw  new RuntimeException("消息对象为空");
            }
            //从缓存中获取当前用户的订单信息
            List<OrderItem> cartList=dto.getOrderItems();
            Order order=new Order();
            List<OrderItem> orderItemList=new ArrayList<>();
            List<StockDto> stockDtos=new ArrayList<>();
            cartList.stream().forEach(stockDto->{
                stockDtos.add(new StockDto(stockDto.getSkuId(),stockDto.getNum()));
            });
            order.setId(dto.getOrder().getId());
            order.setTotalMoney(cartList.stream().mapToDouble(s->s.getTotalMoney()).sum());
            order.setOrderStatus("0");
            order.setCreateTime(new Date());
            order.setPreMoney(cartService.reduceCartPreMoney(dto.getOrder().getUserName()));
            order.setPayStatus("0");
            order.setTotalNum(cartList.stream().mapToInt(OrderItem::getNum).sum());
            order.setUserName(order.getUserName());
            BigDecimal payMoney=new BigDecimal(order.getTotalMoney()).subtract(new BigDecimal(order.getPreMoney()));
            order.setPayMoney(payMoney.doubleValue());
            this.saveOrUpdate(order);
            //4、保存订单详情表
            BigDecimal propMoney=new BigDecimal(order.getPayMoney()).divide(new BigDecimal(order.getTotalMoney()),BigDecimal.ROUND_CEILING);
            for (OrderItem orderItem:cartList){
                orderItem.setId(idWorker.nextId());
                orderItem.setOrderId(order.getId()+"");
                BigDecimal pMoney=new BigDecimal(orderItem.getTotalMoney()).multiply(propMoney);
                orderItem.setPayMoney(pMoney.doubleValue());
                orderItemList.add(orderItem);
            }
            orderItemService.saveBatch(orderItemList);
            //5、清除购物车信息
            cartService.deleteCartItem(dto.getOrder().getUserName());
            //发送同步库存数据至DB的消息
            sender.sendSynStockDBMessage(new SynStockDBMessage(dto.getOrder().getId(),stockDtos));
        }catch (Exception ex){
           // OrderFailMessage failMessage = new OrderFailMessage().stockFail(dto.getOrder().getId());
            //sender.sendCreateOrderFailMessage(failMessage);
            ex.printStackTrace();
            log.error("订单创建执行失败:{}",ex.getMessage());
            isSuc=false;
        }

        return isSuc;
    }

    /**
     *失败消息重发
     */
    @Override
    public void doMessageSendLog(){
        List<MessageSendLog> messageSendLogs = messageSendLogService.listBySendStatus();
        if(!CollectionUtil.isEmpty(messageSendLogs)){
            messageSendLogs.stream().forEach(s->{
                //重试次数超过三次  需人工干预
                if(s.getRetryCount()>3){
                    messageSendLogService.updateMsgStatus(s.getMsgId(),"2");
                    //发送邮件预警
                    //TODO
                    return;
                }else {
                    messageSendLogService.updateMsgRetryCount(s.getMsgId());
                    //重新投递
                    sender.retrySend(s.getMsgExchange(), s.getMsgRouteKey(), s.getMsgContent(), s.getMsgId());
                }
            });
        }
    }

    /**
     * 回退库存
     * @param orderId
     * @return
     */
    @Override
    public boolean returnStock(Long orderId){
        boolean result=false;
        try {
            Set<Map.Entry<Object, Object>> sku_key = redissonClient.getMap("inventory_flow_" + orderId).entrySet();
            StringBuilder sb2 = new StringBuilder();
            List<StockDto> stockDtos=new ArrayList<>();
            String mapKey = "sku_key_";
            String inventory_flow_orderId = "inventory_flow_" + orderId;
            sku_key.forEach((key) -> {
                String skuId = "";
                String num = "";
                if (key.getKey() != null && key.getValue() != null) {
                    skuId = key.getKey().toString().split("sku_key_")[1];
                    num = (String) key.getValue();
                    stockDtos.add(new StockDto(Long.valueOf(skuId),0-Integer.valueOf(num)));
                }
                sb2.append("        local hincrby_result= redis.call('hincrby', '" + mapKey + skuId + "', KEYS[1], " + num + ")\n");
                //退回库存
                sb2.append("         redis.call('hset', '" + mapKey + skuId + "', KEYS[1], hincrby_result)");
                //销量
                sb2.append("     redis.call('hincrby', '" + mapKey + skuId + "', KEYS[2], 0 - " + num + ")");
                //5、删除流水记录
                sb2.append(" redis.call('hdel', '" + inventory_flow_orderId + "', '" + mapKey + skuId + "')");
            });
            sb2.append(" return redis.call('del', '" + inventory_flow_orderId + "')");
            // 脚本里的KEYS参数
            List<Object> keys = new ArrayList<>();
            keys.add("residue");
            keys.add("quantity");
            RScript script = redissonClient.getScript(StringCodec.INSTANCE);
            Object value = script.eval(RScript.Mode.READ_WRITE, sb2.toString(), RScript.ReturnType.VALUE, keys, Arrays.asList());
            result= (Long)value==0L?true:false;
            if(result){
                //发送同步库存数据至DB的消息
                sender.sendSynStockDBMessage(new SynStockDBMessage(orderId,stockDtos));
            }
        }catch (Exception ex){
            log.error("退回库存出错:{}",ex.getMessage());
            ex.printStackTrace();
        }finally {
            return result;
        }



    }
}




