local result = 0
if (redis.call('exists', 'inventory_flow_1628229916826267648') == 0) then
    if (redis.call('exists', 'sku_key_13') == 1) then
        local stock_13 = tonumber(redis.call('hget', 'sku_key_13', KEYS[1]))
        local num_13 = tonumber('10')
        if (stock_13 < num_13) then
            result = -1
            return result
        end
    else
        result = -2
        return result
    end

    if (redis.call('exists', 'sku_key_12') == 1) then
        local stock_12 = tonumber(redis.call('hget', 'sku_key_12', KEYS[1]))
        local num_12 = tonumber('13')
        if (stock_12 < num_12) then
            result = -1
            return result
        end
    else
        result = -2
        return result
    end

    if (redis.call('exists', 'sku_key_11') == 1) then
        local stock_11 = tonumber(redis.call('hget', 'sku_key_11', KEYS[1]))
        local num_11 = tonumber('15')
        if (stock_11 < num_11) then
            result = -1
            return result
        end
    else
        result = -2
        return result
    end
else
    result = -3
    return result
end

local stock_s_13 = tonumber(redis.call('hget', 'sku_key_13', KEYS[1]))
local num_s_13 = tonumber('10')
if (stock_s_13 >= num_s_13) then
    local hincrby_result = redis.call('hincrby', 'sku_key_13', KEYS[1], 0 - num_s_13)
    redis.call('hset', 'sku_key_13', KEYS[1], hincrby_result)
    redis.call('hincrby', 'sku_key_13', KEYS[2], num_s_13)
    redis.call('hset', 'inventory_flow_1628229916826267648', 'sku_key_13', num_s_13)
else
    result = -1
    return result
end

local stock_s_12 = tonumber(redis.call('hget', 'sku_key_12', KEYS[1]))
local num_s_12 = tonumber('13')
if (stock_s_12 >= num_s_12) then
    local hincrby_result = redis.call('hincrby', 'sku_key_12', KEYS[1], 0 - num_s_12)
    redis.call('hset', 'sku_key_12', KEYS[1], hincrby_result)
    redis.call('hincrby', 'sku_key_12', KEYS[2], num_s_12)
    redis.call('hset', 'inventory_flow_1628229916826267648', 'sku_key_12', num_s_12)
else
    result = -1
    return result
end

local stock_s_11 = tonumber(redis.call('hget', 'sku_key_11', KEYS[1]))
local num_s_11 = tonumber('15')
if (stock_s_11 >= num_s_11) then
    local hincrby_result = redis.call('hincrby', 'sku_key_11', KEYS[1], 0 - num_s_11)
    redis.call('hset', 'sku_key_11', KEYS[1], hincrby_result)
    redis.call('hincrby', 'sku_key_11', KEYS[2], num_s_11)
    redis.call('hset', 'inventory_flow_1628229916826267648', 'sku_key_11', num_s_11)
else
    result = -1
    return result
end

return result