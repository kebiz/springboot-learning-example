package com.zengzp.product.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.learning.code.common.model.OrderItem;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author ：zengzhipeng
 * @date ：Created in 2023/2/27 17:29
 * @description：订单mapper类
 * @modified By：
 * @version: 1$
 */
@Repository
@Mapper
public interface OrderItemMapper extends BaseMapper<OrderItem> {
}
