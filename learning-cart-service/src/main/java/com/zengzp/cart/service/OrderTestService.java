package com.zengzp.cart.service;

import com.learning.code.common.model.Order;
import com.learning.code.common.model.OrderDto;
import com.learning.code.common.model.OrderVO;

import java.io.IOException;
import java.util.Map;

public interface OrderTestService {

    /**
     * 创建订单时的前置检查
     * @param order
     * @return
     */
    Map<String,Object> preCheckOrder(Order order) throws IOException;

    /**
     * 通过当前用户名得到用户订单信息
     * @param userName
     * @return
     */
    OrderVO getOrderListByuserName(String userName);
}
