package com.anyi.staservice.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.anyi.commonutils.R;
import com.anyi.staservice.entity.StatisticsDaily;
import com.anyi.staservice.mapper.StatisticsDailyMapper;
import com.anyi.staservice.service.MemberService;
import com.anyi.staservice.service.StatisticsDailyService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author anyi
 * @since 2022-05-18
 */
@Service
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily> implements StatisticsDailyService {

    @Resource
    private MemberService memberService;

    @Override
    public R createSta(String date) {
        // 1.远程调用返回日期数据
        Integer registerNum = memberService.getRegisterNum(date);

        // 2.封装成对象存储
        // 2.1 生成登录人数，播放数新增课程数
        Integer loginNum = RandomUtil.randomInt(100,200);
        Integer watchNum = RandomUtil.randomInt(100,200);
        Integer addCourseNum = RandomUtil.randomInt(100,200);

        StatisticsDaily statisticsDaily = new StatisticsDaily();
        statisticsDaily.setDateCalculated(date);
        statisticsDaily.setRegisterNum(registerNum);
        statisticsDaily.setCourseNum(addCourseNum);
        statisticsDaily.setLoginNum(loginNum);
        statisticsDaily.setVideoViewNum(watchNum);

        // 3.1存储到数据库中
        // 3.2 防止数据重复先删除数据库中的数据
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.eq("date_calculated", date);
        StatisticsDaily one = getOne(wrapper);
        if (one !=null){
            remove(wrapper);
        }
        // 3.3 存储
        save(statisticsDaily);
        return R.ok();
    }

    // 根据日期区间返回数据
    @Override
    public R getData(String type, String begin, String end) {

        // 1. 根据区间获取所有日期 和日期type属性值
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.between("date_calculated", begin, end);
        wrapper.select("date_calculated",type);

        List<StatisticsDaily> list = list(wrapper);
        // 2. 将数据分别封装到list集合中
        List<String > dateList = new ArrayList<>();

        List<Integer> numList = new ArrayList<>();

        for (StatisticsDaily statisticsDaily : list) {
            dateList.add(statisticsDaily.getDateCalculated());
            switch (type){
                case "login_type":
                    numList.add(statisticsDaily.getLoginNum());
                    break;
                case "register_num":
                    numList.add(statisticsDaily.getRegisterNum());
                    break;
                case "video_view_num":
                    numList.add(statisticsDaily.getVideoViewNum());
                    break;
                case "course_num":
                    numList.add(statisticsDaily.getCourseNum());
                    break;
                default:
                    break;
            }
        }
        return R.ok().data("dateList",dateList).data("numList",numList);
    }
}
