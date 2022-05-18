package com.anyi.order.controller;


import com.anyi.commonutils.R;
import com.anyi.order.entity.PayLog;
import com.anyi.order.service.PayLogService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author anyi
 * @since 2022-05-17
 */
@RestController
@RequestMapping("/edu/order-pay-log/")
public class PayLogController {
    @Resource
    private PayLogService payLogService;

    // 根据订单号生成微信收款码
    @GetMapping("/getWxPay/{orderNo}")
    public R getWxPay(@PathVariable String orderNo){
        Map map = payLogService.createNative(orderNo);
        return R.ok().data(map);
    }

}

