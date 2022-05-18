package com.anyi.order.service;

import com.anyi.order.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author anyi
 * @since 2022-05-17
 */
public interface OrderService extends IService<Order> {

    // 生成订单
    String createOrder(String courseId, String userId);
}
