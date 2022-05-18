package com.anyi.order.service.impl;

import com.anyi.order.entity.Order;
import com.anyi.order.entity.PayLog;
import com.anyi.order.mapper.PayLogMapper;
import com.anyi.order.service.OrderService;
import com.anyi.order.service.PayLogService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.wxpay.sdk.WXPayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 支付日志表 服务实现类
 * </p>
 *
 * @author anyi
 * @since 2022-05-17
 */
@Service
public class PayLogServiceImpl extends ServiceImpl<PayLogMapper, PayLog> implements PayLogService {
    @Autowired
    private OrderService orderService;

    @Override
    public Map createNative(String orderNo) {
        return null;
    }
}
