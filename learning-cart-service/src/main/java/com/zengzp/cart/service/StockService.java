package com.zengzp.cart.service;

import com.learning.code.common.model.OrderItem;
import org.redisson.api.RScript;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.StringCodec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author ：zengzhipeng
 * @date ：Created in 2022/8/17 10:36
 * @description：扣减库存Service
 * @modified By：
 * @version: 1.0$
 */
@Service
public class StockService {
    Logger logger = LoggerFactory.getLogger(StockService.class);
    @Autowired
    private RedissonClient redissonClient;


    /**
     * 扣库存
     *
     * @return 扣减之后剩余的库存【-2:库存未初始化;-3:重复订单; -1:库存不足;  等于0:扣库存成功】
     */
    private long stock(String luaStr) throws IOException {
        // 脚本里的KEYS参数
        List<Object> keys = new ArrayList<>();
        keys.add("residue");
        keys.add("quantity");
        RScript script = redissonClient.getScript(StringCodec.INSTANCE);
        //Long value=script.eval(RScript.Mode.READ_WRITE,new ResourceScriptSource(new ClassPathResource("stock.lua")).getScriptAsString(), RScript.ReturnType.VALUE, keys, Arrays.asList() );
        Long value=script.eval(RScript.Mode.READ_WRITE,luaStr, RScript.ReturnType.VALUE, keys, Arrays.asList() );

        logger.info("======扣库存返回结果====={}",value);
       return value;
    }
    /**
     * 扣减库存
     *
     * @param orderItemList
     */
    public long deductionStock(List<OrderItem> orderItemList, long orderId) {
        StringBuilder sb = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        String key="sku_key_";
        long result=0;
        String inventory_flow_orderId="inventory_flow_"+orderId;
        //1、验证是否重复订单
        sb.append(" local result=0 ");
        sb.append(" if (redis.call('exists', '"+inventory_flow_orderId+"') == 0) then");
        for (OrderItem orderItem:orderItemList){
            //2、验证商品是否初始化
            sb.append("  if (redis.call('exists', '"+key+orderItem.getSkuId()+"') == 1) then");
            //3、验证商品是否还有库存
            sb.append("    local stock_"+orderItem.getSkuId()+" = tonumber(redis.call('hget','"+key+orderItem.getSkuId()+"',KEYS[1]))");
            sb.append("    local num_"+orderItem.getSkuId()+" = tonumber('"+orderItem.getNum()+"')");
            sb.append("    if (stock_"+orderItem.getSkuId()+" < num_"+orderItem.getSkuId()+") then");
            //返回-1库存不够
            sb.append("    result=-1");
            sb.append("    return result");
            sb.append("    end");
            sb.append("     else");
            //返回-2商品未初始化
            sb.append("    result=-2");
            sb.append("   return result");
            sb.append("   end");

            //4、扣减库存
            sb2.append("    local stock_s_"+orderItem.getSkuId()+" = tonumber(redis.call('hget','"+key+orderItem.getSkuId()+"',KEYS[1]))");
            sb2.append("    local num_s_"+orderItem.getSkuId()+" = tonumber('"+orderItem.getNum()+"')");
            sb2.append("    if (stock_s_"+orderItem.getSkuId()+" >= num_s_"+orderItem.getSkuId()+") then");
            sb2.append("        local hincrby_result= redis.call('hincrby', '"+key+orderItem.getSkuId()+"', KEYS[1], 0 - num_s_"+orderItem.getSkuId()+")\n");
            //剩余库存
            sb2.append("         redis.call('hset', '"+key+orderItem.getSkuId()+"', KEYS[1], hincrby_result)");
            //销量
            sb2.append("     redis.call('hincrby', '"+key+orderItem.getSkuId()+"', KEYS[2], num_s_"+orderItem.getSkuId()+")");
            //5、增加流水记录
            sb2.append(" redis.call('hset', '"+inventory_flow_orderId+"', '"+key+orderItem.getSkuId()+"', num_s_"+orderItem.getSkuId()+")");
            sb2.append("     else");
            sb2.append("    result=-1");
            sb2.append("    return result");
            sb2.append("    end");

        }
        sb.append("     else");
        sb.append("    result=-3");
        sb.append("   return result");
        sb.append("   end");
        //把lua命令合并
        sb.append(sb2).append(" return result");
        try {
            result=this.stock(sb.toString());
        }catch (Exception ex){
            logger.error("扣减库存缓存服务出错：{}",ex.getMessage());
            result =-4;
        }finally {
            return result;
        }


    }
}
