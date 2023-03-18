package com.zengzp.cart.controller;

import com.learning.code.common.model.Order;
import com.learning.dubbo.CartService;
import com.zengzp.cart.contant.CacheKey;
import com.zengzp.cart.service.OrderTestService;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

/**
 * @author ：zengzhipeng
 * @date ：Created in 2023/2/23 15:47
 * @description：订单控制类
 * @modified By：
 * @version: 1$
 */
@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderTestService orderTestService;
    @Autowired
    private CartService cartService;
    @Autowired
    private RedissonClient redissonClient;
    @Autowired
    private RedisTemplate redisTemplate;
    @PostMapping("/create")
    public Object createOrder(@RequestBody Order order) throws IOException {
        Map<String, Object> map = orderTestService.preCheckOrder(order);
        return map;
    }
    @PostMapping("/addCart")
    public Object createOrder(@RequestParam(name = "skuId") Long skuId, @RequestParam(name = "productNum") Integer productNum) throws IOException {
        boolean result= cartService.addCart("userName",skuId,productNum);
        return result;
    }
    @GetMapping("/getInventoryFlow/{orderId}")
    public Object createOrder(@PathVariable(value = "orderId") @NotNull Long orderId) throws IOException {
        RMap<Object, Object> map = redissonClient.getMap("sku_key_1");
        //Set<Map.Entry<Object, Object>> sku_key = redissonClient.getMap("inventory_flow_"+orderId).entrySet();
        return "退回前库存==========" + map.get("residue")+"退回前销量==========" + map.get("quantity");
        //return sku_key;
    }

    @GetMapping("/getStockBySkuId/{skuId}")
    public Object getStockBySkuId(@PathVariable(value = "skuId") @NotNull Long skuId) throws InterruptedException {
        return redisTemplate.boundHashOps(CacheKey.CART_LIST.toString()).get(skuId);
    }
}
