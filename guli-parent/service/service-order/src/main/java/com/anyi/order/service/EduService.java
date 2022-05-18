package com.anyi.order.service;

import com.anyi.commonutils.vo.EduCourseVo;
import com.anyi.commonutils.vo.UcenterMemberVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author 安逸i
 * @version 1.0
 */
@FeignClient("service-edu")
public interface EduService {
    // 根据id获取课程信息
    @GetMapping("/edu/course/vo/{id}")
    public EduCourseVo getCourseVoById(@PathVariable String id);

    // 根据id获取讲师信息
    @GetMapping("/edu/member/user/vo/{id}")
    public UcenterMemberVo getUserVoById(@PathVariable String id);

}
