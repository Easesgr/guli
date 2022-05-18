package com.anyi.serviceedu.controller;


import com.anyi.commonutils.R;
import com.anyi.commonutils.ResultCode;
import com.anyi.servicebase.exception.GuliException;
import com.anyi.serviceedu.entity.EduVideo;
import com.anyi.serviceedu.service.EduVideoService;

import com.anyi.serviceedu.service.VodService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.xml.transform.Result;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author anyi
 * @since 2022-05-14
 */
@RestController
@Api(tags = "小节管理")
@CrossOrigin
@RequestMapping("/edu/video")
public class EduVideoController {
    // 根据id查询小节
    @Resource
    private EduVideoService eduVideoService;

    @Resource
    private VodService vodService;
    @GetMapping("/{id}")
    public R getVideoById(@PathVariable String id){
        EduVideo video = eduVideoService.getById(id);
        return R.ok().data("video",video);
    }
    // 删除小节
    @DeleteMapping("/{id}")
    public R deleteVideoById(@PathVariable String id){
        boolean flag = eduVideoService.removeById(id);

        // 删除小节的同时将阿里云视频删除
        EduVideo video = eduVideoService.getById(id);
        if (video.getVideoSourceId() != null){
            R r = vodService.deleteVod(video.getVideoSourceId());
            if (r.getCode() == ResultCode.ERROR){
                throw new GuliException(ResultCode.ERROR,"删除阿里云视频失败");
            }
        }
        if (!flag){
            throw new GuliException(ResultCode.ERROR,"删除失败！");
        }
        return R.ok();
    }
    // 添加小节
    @PostMapping("/add")
    public R addVideo(@RequestBody EduVideo eduVideo){
        boolean flag = eduVideoService.save(eduVideo);
        if (!flag){
            throw new GuliException(ResultCode.ERROR,"添加失败！");
        }
        return R.ok();
    }
    @PutMapping("/update")
    public R updateVideo(@RequestBody EduVideo eduVideo){
        boolean flag = eduVideoService.saveOrUpdate(eduVideo);
        if (!flag){
            throw new GuliException(ResultCode.ERROR,"更新失败！");
        }
        return R.ok();
    }

}


