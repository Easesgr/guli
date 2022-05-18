package com.anyi.staservice.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author 安逸i
 * @version 1.0
 */
@FeignClient("service-edu")
public interface MemberService {
    @GetMapping("/edu/member/registerNum/{date}")
    public Integer getRegisterNum(@PathVariable String date);
}
