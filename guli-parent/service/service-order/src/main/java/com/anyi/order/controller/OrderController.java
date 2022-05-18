package com.anyi.order.controller;


import com.anyi.commonutils.R;
import com.anyi.commonutils.utils.JwtUtils;
import com.anyi.commonutils.vo.EduCourseVo;
import com.anyi.order.entity.Order;
import com.anyi.order.service.EduService;
import com.anyi.order.service.OrderService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author anyi
 * @since 2022-05-17
 */
@RequestMapping("/order-service")
@RestController
@CrossOrigin
@Slf4j
public class OrderController {
    @Resource
    private OrderService orderService;
    // 生成订单
    @PostMapping("/createOrder/{courseId}")
    public R createOrder(@PathVariable String courseId, HttpServletRequest request){
        log.info(courseId);
        String userId = JwtUtils.getMemberIdByJwtToken(request);

        String order_no = orderService.createOrder(courseId,userId);

        return R.ok().data("orderId",order_no);
    }
    // 根据20为订单号查询订单
    @GetMapping("/getOrderInfo/{orderId}")
    public R getOrderById(@PathVariable String orderId){
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no", orderId);
        Order order = orderService.getOne(wrapper);
        return R.ok().data("order",order);
    }


}

