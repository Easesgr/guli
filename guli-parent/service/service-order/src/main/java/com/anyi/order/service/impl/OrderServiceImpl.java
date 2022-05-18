package com.anyi.order.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.anyi.commonutils.utils.JwtUtils;
import com.anyi.commonutils.vo.EduCourseVo;
import com.anyi.commonutils.vo.UcenterMemberVo;
import com.anyi.order.entity.Order;
import com.anyi.order.mapper.OrderMapper;
import com.anyi.order.service.EduService;
import com.anyi.order.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author anyi
 * @since 2022-05-17
 */
@Service
@Slf4j
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Resource
    private EduService eduService;
    @Override
    public String createOrder(String courseId, String userId) {
        // 根据courseId 获取课程信息
        EduCourseVo course = eduService.getCourseVoById(courseId);
        log.info(course.toString());
        // 查询用户信息
        UcenterMemberVo user = eduService.getUserVoById(userId);
        // 生成订单号Order order = new Order();
        Order order = new Order();
        String orderId = RandomUtil.randomString(20);
        order.setOrderNo(orderId);
        order.setCourseId(course.getId());
        order.setCourseTitle(course.getTitle());
        order.setCourseCover(course.getCover());
        order.setTeacherId("anyi");
        order.setMemberId(user.getId());
        order.setNickname(user.getNickname());
        order.setMobile(user.getMobile());
        order.setTotalFee(course.getPrice());
        order.setPayType(1);
        order.setStatus(0);
        // 存储订单
        save(order);
        // 返回存储后的订单号,方便后面获取支付二维码
        return orderId;
    }
}
