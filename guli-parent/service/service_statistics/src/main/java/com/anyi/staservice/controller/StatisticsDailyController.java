package com.anyi.staservice.controller;


import com.anyi.commonutils.R;
import com.anyi.staservice.service.MemberService;
import com.anyi.staservice.service.StatisticsDailyService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author anyi
 * @since 2022-05-18
 */
@RestController
@CrossOrigin
@RequestMapping("/sta-service")
public class StatisticsDailyController {

    @Resource
    private StatisticsDailyService staService;



    // 根据日期获取每天的注册人数
    @GetMapping("/createSta/{date}")
    public R createSta(@PathVariable String date){
        return staService.createSta(date);
    }
    // 根据日区间和需要显示的列返回数据
    @PostMapping("/getData/{type}/{begin}/{end}")
    public R getData(@PathVariable String type,
                     @PathVariable String begin,
                     @PathVariable String end){
        return staService.getData(type,begin,end);
    }
}

