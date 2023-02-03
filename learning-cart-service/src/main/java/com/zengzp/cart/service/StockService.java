package com.zengzp.cart.service;

import cn.hutool.core.lang.Assert;
import com.zengzp.cart.contant.CacheKey;
import com.zengzp.cart.entity.OrderItem;
import com.zengzp.cart.entity.Sku;
import org.redisson.api.RLock;
import org.redisson.api.RMap;
import org.redisson.api.RScript;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.StringCodec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

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

    /**
     * 不限库存
     */
    public static final long UNINITIALIZED_STOCK = -3L;

    /**
     * Redis 客户端
     */
    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;
    @Autowired
    private RedissonClient redissonClient;
    /**
     * 执行扣库存的脚本
     */
    public static final String STOCK_LUA;
    /**
     * 执行扣库存的脚本
     */
    public static final String STOCK_QUA_LUA;
    static {
        /**
         *
         * @desc 扣减库存Lua脚本
         * 库存（stock）-1：表示不限库存
         * 库存（stock）0：表示没有库存
         * 库存（stock）大于0：表示剩余库存
         *
         * @params 库存key
         * @return
         *   -3:库存未初始化
         *   -2:库存不足
         *   -1:不限库存
         *   大于等于0:剩余库存（扣减之后剩余的库存）
         *      redis缓存的库存(value)是-1表示不限库存，直接返回1
         */
        StringBuilder sb = new StringBuilder();
        sb.append("if (redis.call('exists', KEYS[1]) == 1) then");
        sb.append("    local stock = tonumber(redis.call('get', KEYS[1]));");
        sb.append("    local num = tonumber(ARGV[1]);");
        sb.append("    if (stock == -1) then");
        sb.append("        return -1;");
        sb.append("    end;");
        sb.append("    if (stock >= num) then");
        sb.append("        return redis.call('incrby', KEYS[1], 0 - num);");
        sb.append("    end;");
        sb.append("    return -2;");
        sb.append(" end;");
        sb.append(" return -3;");
        STOCK_LUA = sb.toString();
    }
    static {
        /**
         *
         * @desc 扣减库存Lua脚本
         * 库存（stock）-1：表示不限库存
         * 库存（stock）0：表示没有库存
         * 库存（stock）大于0：表示剩余库存
         *
         * @params 库存key
         * @return
         *   -3:库存未初始化
         *   -2:库存不足
         *   -1:不限库存
         *   大于等于0:剩余库存（扣减之后剩余的库存）
         *      redis缓存的库存(value)是-1表示不限库存，直接返回1
         */
        StringBuilder sb = new StringBuilder();
        sb.append("if (redis.call('exists', KEYS[1]) == 1) then");
        sb.append("    local stock = tonumber(redis.call('hget', KEYS[1],KEYS[2]));");
        sb.append("    local num = tonumber(ARGV[1]);");
        sb.append("    if (stock == -1) then");
        sb.append("        return -1;");
        sb.append("    end;");
        sb.append("    if (stock >= num) then");
        sb.append("        local result= redis.call('hincrby', KEYS[1], KEYS[2], 0 - num);\n");
        sb.append("         redis.call('hset', KEYS[1], KEYS[2], result);");
        sb.append("     redis.call('hincrby', KEYS[1], KEYS[3], num);");
        sb.append("     return result\n");
        sb.append("    end;");
        sb.append("    return -2;");
        sb.append("   end;");
        sb.append("   return -3;");
        STOCK_QUA_LUA = sb.toString();
    }
    /**
     * @param key           库存key
     * @param expire        库存有效时间,单位秒
     * @param num           扣减数量
     * @return -2:库存不足; -1:不限库存; 大于等于0:扣减库存之后的剩余库存
     */
    public long stock(String key, long expire, int num) {
        long stock = stock(key, num);
        // 初始化库存
        initStock(key,expire,num,stock);
        return stock;
    }

    /**
     * 初始化库存
     * @param key
     * @param expire
     * @param num
     * @param stock
     * @return
     */
    public long initStock(String key, long expire, int num,long stock){
        if (stock == UNINITIALIZED_STOCK) {
            RLock lock = redissonClient.getLock(key);
            try {
                // 获取锁
                if (lock.tryLock(30,TimeUnit.SECONDS)) {
                    // 双重验证，避免并发时重复回源到数据库
                    stock = stock(key, num);
                    if (stock == UNINITIALIZED_STOCK) {
                        // 获取初始化库存
//                        final int initStock = stockCallback.getStock();
                        RMap<Object, Object> stock1 = redissonClient.getMap(key);
                        stock1.put("residue",1000);
                        stock1.put("quantity",0);
//                        // 将库存设置到redis
//                        redisTemplate.opsForValue().set(key, initStock, expire, TimeUnit.SECONDS);
                        // 调一次扣库存的操作
                        stock = stock(key, num);
                    }
                }
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            } finally {
                lock.unlock();
            }

        }
        return stock;
    }

    /**
     * 加库存(还原库存)
     *
     * @param key    库存key
     * @param num    库存数量
     * @return
     */
    public long addStock(String key, int num) {

        return addStock(key, 60L, num);
    }

    /**
     * 加库存
     *
     * @param key    库存key
     * @param expire 过期时间（秒）
     * @param num    库存数量
     * @return
     */
    public long addStock(String key, Long expire, int num) {
        boolean hasKey = redisTemplate.hasKey(key);
        // 判断key是否存在，存在就直接更新
        if (hasKey) {
            return redisTemplate.opsForValue().increment(key, num);
        }

        Assert.notNull(expire,"初始化库存失败，库存过期时间不能为null");
        RLock lock = redissonClient.getLock(key);
        try {
            if (lock.tryLock(30,TimeUnit.SECONDS)) {
                // 获取到锁后再次判断一下是否有key
                hasKey = redisTemplate.hasKey(key);
                if (!hasKey) {
                    // 初始化库存
                    redisTemplate.opsForValue().set(key, num, expire, TimeUnit.SECONDS);
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            lock.unlock();
        }

        return num;
    }

    /**
     * 获取库存
     *
     * @param key 库存key
     * @return -1:不限库存; 大于等于0:剩余库存
     */
    public int getStock(String key) {
        RMap<Object, Object> stock1 = redissonClient.getMap(key);
        stock1.put("residue",1000);
        stock1.put("quantity",0);

        System.out.println("======"+stock1.get("quantity"));
        //Integer stock = (Integer) redisTemplate.opsForValue().get(key);
        return  0;
       // return stock == null ? -1 : stock;
    }

    /**
     * 扣库存
     *
     * @param key 库存key
     * @param num 扣减库存数量
     * @return 扣减之后剩余的库存【-3:库存未初始化; -2:库存不足; -1:不限库存; 大于等于0:扣减库存之后的剩余库存】
     */
    private Long stock(String key, int num) {
        // 脚本里的KEYS参数
        List<Object> keys = new ArrayList<>();
        keys.add(key);
        keys.add("residue");
        keys.add("quantity");
        // 脚本里的ARGV参数
        List<Object> args = new ArrayList<>();
        args.add(Integer.toString(num));
        RScript script = redissonClient.getScript(StringCodec.INSTANCE);
       Long value=script.eval(RScript.Mode.READ_WRITE,STOCK_QUA_LUA, RScript.ReturnType.VALUE, keys, num);
       /* long result = redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                Object nativeConnection = connection.getNativeConnection();
                // 集群模式和单机模式虽然执行脚本的方法一样，但是没有共同的接口，所以只能分开执行
                // 集群模式
                if (nativeConnection instanceof JedisCluster) {
                    return (Long) ((JedisCluster) nativeConnection).eval(STOCK_LUA, keys, args);
                }

                // 单机模式
                else if (nativeConnection instanceof Jedis) {
                    return (Long) ((Jedis) nativeConnection).eval(STOCK_LUA, keys, args);
                }
                return UNINITIALIZED_STOCK;
            }
        });*/
       System.out.println(String.valueOf(value));
        return value;
    }
    /**
     * 扣减库存
     *
     * @param orderItemList
     */
    public boolean deductionStock(List<OrderItem> orderItemList) {
        //1:扣减sku库存
        //2:增加销售数量
        boolean isDedction=false;
        for (OrderItem orderItem:orderItemList){
            Sku sku=(Sku) redisTemplate.boundHashOps(CacheKey.SKU_LIST.toString()).get(orderItem.getSkuId());
        if(sku==null){
            isDedction=false;
            break;
        }
        if (!"1".equals(sku.getStatus())){
            isDedction=false;
            break;
        }
        if(orderItem.getNum()>sku.getNum()){
            isDedction=false;
            break;
        }
        if(!isDedction){
            long stock=this.stock(String.valueOf(orderItem.getSkuId()),orderItem.getNum());
            if(stock<0){
                isDedction=false;
                break;
            }
            isDedction=true;
        }
        }
        return isDedction;
    }
}