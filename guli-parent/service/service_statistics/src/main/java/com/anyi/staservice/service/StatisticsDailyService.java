package com.anyi.staservice.service;

import com.anyi.commonutils.R;
import com.anyi.staservice.entity.StatisticsDaily;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author anyi
 * @since 2022-05-18
 */
public interface StatisticsDailyService extends IService<StatisticsDaily> {

    // 根据日期生成当天数据并返回
    R createSta(String date);

    R getData(String type, String begin, String end);
}
