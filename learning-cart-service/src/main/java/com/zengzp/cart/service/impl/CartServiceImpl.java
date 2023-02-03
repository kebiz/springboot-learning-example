package com.zengzp.cart.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import com.zengzp.cart.contant.CacheKey;
import com.zengzp.cart.entity.OrderItem;
import com.zengzp.cart.entity.Preferential;
import com.zengzp.cart.entity.Sku;
import com.zengzp.cart.service.CartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author ：zengzhipeng
 * @date ：Created in 2023/1/3 11:57
 * @description：购物车实现类
 * @modified By：
 * @version: 1$
 */
@Service
@Slf4j
public class CartServiceImpl implements CartService {
    @Autowired
    private RedisTemplate redisTemplate;
    /**
     * 根据用户名在缓存中查询购物车
     *
     * @param userName
     * @return
     */
    @Override
    public List<Map<String, Object>> findCartList(String userName) {
        List<Map<String, Object>> list=(List<Map<String, Object>>)  redisTemplate.boundHashOps(CacheKey.CART_LIST.toString()).get(userName);
        if(list ==null){
            list =new ArrayList<>();
        }
        return list;
    }

    /**
     * 添加到购物车
     *
     * @param userName 用户ID
     * @param skuId    skuId
     * @param num      数量
     * @return
     */
    @Override
    public boolean addCart(String userName, long skuId, int num) {
        List<Map<String, Object>> cartList = this.findCartList(userName);
        boolean flag=false;
        try {
            for (Map<String, Object> objectMap:cartList){
                OrderItem orderItem  =(OrderItem)objectMap.get("item");
                //如果该用户的购物车存在此商品
                if(skuId==orderItem.getSkuId()){
                    if(orderItem.getNum()<=0){
                        //如果购物车的数量小于等于0，则删除
                        cartList.remove(objectMap);
                        break;
                    }
                    //单个商品重量
                    BigDecimal unitW=new BigDecimal(orderItem.getWeight()).divide(new BigDecimal(orderItem.getNum().toString()));
                    BigDecimal weight=unitW.multiply(new BigDecimal(String.valueOf(num)));
                    orderItem.setWeight(weight.doubleValue());
                    //数量
                    orderItem.setNum(orderItem.getNum()+num);
                    //总金额
                    BigDecimal totalMoney= new BigDecimal(orderItem.getPrice()).multiply(new BigDecimal(orderItem.getNum().toString()));
                    orderItem.setTotalMoney(totalMoney.doubleValue());
                    if(orderItem.getNum()<=0){
                        //如果购物车的数量小于等于0，则删除
                        cartList.remove(objectMap);
                    }
                    flag=true;
                    break;
                }
            }
            if(!flag) {
                Sku sku  =(Sku)redisTemplate.boundHashOps(CacheKey.SKU_LIST.toString()).get(skuId);
                if(sku==null){
                    throw new RuntimeException("sku不存在");
                }
                if(sku.getNum()<=0){
                    throw new RuntimeException("商品库存不足");
                }
                if(!"1".equals(sku.getStatus())){
                    throw new RuntimeException("商品已下架");
                }
                //如果购物车没有就新增
                OrderItem item=new OrderItem();
                item.setPrice(sku.getPrice());
                item.setNum(num);
                item.setName(sku.getName());
                item.setCategory1Id(sku.getCategoryId());
                //设置二级、三级分类ID
                item.setCategory2Id(2);
                item.setCategory3Id(3);
                item.setImage(sku.getImage());
                item.setSpuId(sku.getSpuId());
                item.setSkuId(skuId);
                BigDecimal totalMoney= new BigDecimal(sku.getPrice()).multiply(new BigDecimal(String.valueOf(num)));
                item.setTotalMoney(totalMoney.doubleValue());
                if(sku.getWeight()==null){
                    sku.setWeight(0.00);
                }
                BigDecimal weight= new BigDecimal(sku.getWeight()).multiply(new BigDecimal(String.valueOf(num)));
                item.setWeight(weight.doubleValue());

                Map<String,Object> map = new HashMap<>(2);
                map.put("item",item);
                map.put("checked",true);
                cartList.add(map);

            }
            //更新购物车缓存
            redisTemplate.boundHashOps(CacheKey.CART_LIST.toString()).put(userName,cartList);
            flag=true;
        }catch (Exception ex){
            ex.printStackTrace();
            log.error(ex.getMessage());
        }

        return flag;
    }

    /**
     * 购物车选中状态
     *
     * @param userName 当前用户
     * @param skuId
     * @param checked  是否选中
     */
    @Override
    public void updateChecked(String userName, long skuId, boolean checked) {
        List<Map<String, Object>> cartList = this.findCartList(userName);
        for (Map<String, Object> map:cartList){
            OrderItem item=(OrderItem)map.get("item");
            if(item.getSkuId()==skuId){
                map.put("checked",checked);
                break;
            }
        }
        //更新购物车缓存
        redisTemplate.boundHashOps(CacheKey.CART_LIST.toString()).put(userName,cartList);
    }

    /**
     * 删除选中购物车选项
     *
     * @param userName
     */
    @Override
    public void deleteCartItem(String userName) {
        List<Map<String, Object>> cartList = this.findCartList(userName).stream().filter(s -> (boolean) s.get("checked") == false).collect(Collectors.toList());
        //更新购物车缓存
        redisTemplate.boundHashOps(CacheKey.CART_LIST.toString()).put(userName,cartList);
    }

    /**
     * 计算购物车选中商品的总优惠金额
     *
     * @param userName
     * @return
     */
    @Override
    public double reduceCartPreMoney(String userName) {
        List<OrderItem> cartList = this.findCartList(userName).stream()
                .filter(s -> (boolean) s.get("checked") == true)
                .map(t->(OrderItem)t.get("item"))
                .collect(Collectors.toList());
        //利用stream把categoryId分组计算优惠金额
        BigDecimal totalMoney =new BigDecimal("0");
        Map<Long, DoubleSummaryStatistics> cartMap = cartList.stream().collect(Collectors.groupingBy(OrderItem::getCategory3Id, Collectors.summarizingDouble(OrderItem::getTotalMoney)));
            for (Long tt:cartMap.keySet()){
                double  money=cartMap.get(tt).getSum();
                Set<Preferential> members = redisTemplate.boundSetOps(CacheKey.PRE_LIST.toString()).members();
                Optional<Preferential> option = members.stream()
                        .filter(s -> BigDecimal.valueOf(money).compareTo(BigDecimal.valueOf(s.getBuyMoney())) > 0)
                        .filter(s -> s.getCategoryId().equals(tt))
                        .filter(s -> s.getStatus().equals("0") == true)
                        .filter(s -> DateUtil.compare(s.getEndTime(), new Date()) > 0)
                        .filter(s -> DateUtil.compare(new Date(), s.getStartTime()) > 0)
                        .sorted(Comparator.comparing(Preferential::getPreMoney).reversed())
                        .findFirst();
                        //.max((a, b) -> BigDecimal.valueOf(a.getPreMoney()).compareTo(BigDecimal.valueOf(b.getBuyMoney())));
                if(option!=null && option.isPresent()) {
                    Preferential   preferential=option.get();
                    if ("1".equals(preferential.getType())) {
                        //类型为1代表优惠金额翻倍
                        BigDecimal divide = BigDecimal.valueOf(money).divide(BigDecimal.valueOf(preferential.getBuyMoney()));
                        int intValue = divide.intValue();
                        BigDecimal preMoney = BigDecimal.valueOf(preferential.getPreMoney()).multiply(BigDecimal.valueOf(intValue));
                        totalMoney = totalMoney.add(preMoney);
                    } else {
                        totalMoney = totalMoney.add(BigDecimal.valueOf(preferential.getPreMoney()));
                    }
                }
            }

        return totalMoney.doubleValue();
    }

}
