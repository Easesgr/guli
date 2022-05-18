package com.anyi.order.service;

import com.anyi.order.entity.PayLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 支付日志表 服务类
 * </p>
 *
 * @author anyi
 * @since 2022-05-17
 */
public interface PayLogService extends IService<PayLog> {

    Map createNative(String orderNo);
}
