package com.anyi.order.mapper;

import com.anyi.commonutils.R;
import com.anyi.order.entity.PayLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

/**
 * <p>
 * 支付日志表 Mapper 接口
 * </p>
 *
 * @author anyi
 * @since 2022-05-17
 */
public interface PayLogMapper extends BaseMapper<PayLog> {


}
