package com.anyi.serviceedu.service;

import com.anyi.commonutils.R;
import com.anyi.serviceedu.service.impl.VodServiceImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author 安逸i
 * @version 1.0
 */
@FeignClient(value = "service-vod" ,fallback = VodServiceImpl.class)
@Component
public interface VodService {
    @DeleteMapping("/vod-service/deleteVideo/{id}")
    public R deleteVod(@PathVariable String id);
    @DeleteMapping("/vod-service/batchDeleteVideo")
    public R batchDeleteVideo(@RequestParam("videoIds") List<String > videoIds);
}
