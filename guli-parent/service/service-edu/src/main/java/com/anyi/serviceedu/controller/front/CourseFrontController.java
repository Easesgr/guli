package com.anyi.serviceedu.controller.front;

import com.anyi.commonutils.R;
import com.anyi.serviceedu.entity.query.CourseFrontQuery;
import com.anyi.serviceedu.service.EduCourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author 安逸i
 * @version 1.0
 */
@RestController
@CrossOrigin
@Api(tags = "前台课程管理")
@RequestMapping("/edu/front/course")
public class CourseFrontController {

    @Resource
    private EduCourseService eduCourseService;

    // 分页查询课程信息
    @PostMapping("getCourseInfo/{page}/{limit}")
    @ApiOperation("前台课程分页查询")
    public R getCourseInfo(@PathVariable Integer page,
                           @PathVariable Integer limit,
                           @RequestBody(required = false) CourseFrontQuery courseVo){
        return eduCourseService.getFrontCourse(page,limit,courseVo);
    }
    // 根据id查询课程的详情信息
    @GetMapping("/getDetailInfo/{id}")
    public R getDetailInfo(@PathVariable String id){
        return eduCourseService.getDetailInfo(id);
    }
}
