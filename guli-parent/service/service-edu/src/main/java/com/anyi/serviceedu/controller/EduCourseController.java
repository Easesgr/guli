package com.anyi.serviceedu.controller;


import com.anyi.commonutils.CommonFields;
import com.anyi.commonutils.R;
import com.anyi.commonutils.ResultCode;
import com.anyi.commonutils.vo.EduCourseVo;
import com.anyi.servicebase.exception.GuliException;
import com.anyi.serviceedu.entity.EduCourse;
import com.anyi.serviceedu.entity.query.CourseQuery;
import com.anyi.serviceedu.entity.vo.CourseVo;
import com.anyi.serviceedu.service.EduCourseService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author anyi
 * @since 2022-05-14
 */
@RestController
@CrossOrigin
@Api(tags = "课程管理")
@RequestMapping("/edu/course")
public class EduCourseController {

    @Resource
    private EduCourseService courseService;

    @ApiOperation("添加课程")
    @PostMapping("/addCourseInfo")
    public R addCourseInfo(@RequestBody CourseVo courseVo){
        return courseService.addCourseInfo(courseVo);
    }

    @ApiOperation("根据课程id获取课程信息")
    @GetMapping("/{courseId}")
    public R getCourseById(@PathVariable String courseId){
        return courseService.getCourseById(courseId);
    }

    @ApiOperation("更新course信息")
    @PutMapping("/update")
    public R update(@RequestBody CourseVo courseVo){
        return courseService.updateCourse(courseVo);
    }

    // 获取发布课程信息
    @ApiOperation("获取发布课程信息")
    @GetMapping("publishCourseInfo/{id}")
    public R publishCourseInfo(@PathVariable String id){
        return courseService.getPublishCourseInfo(id);
    }
    // 发布课程，更改状态
    @ApiOperation("更改课程状态")
    @PostMapping("/updateStatus/{id}")
    public R updateStatus(@PathVariable String id){
        EduCourse course = new EduCourse();
        course.setId(id);
        course.setStatus(CommonFields.COURSE_STATUS_PUBLISHED);
        boolean b = courseService.updateById(course);
        if (!b){
            throw new GuliException(ResultCode.ERROR,"发布失败");
        }
        return R.ok();
    }
    // 获取所有课程
    @GetMapping("/allCourses")
    public R getAllCourse(){
        List<EduCourse> list = courseService.list(null);
        return R.ok().data("list",list);
    }
    // 分页查询和条件查询课程
    @PostMapping("/getCoursesPage/{page}/{limit}")
    public R getPageCourse(@PathVariable Integer page,@PathVariable Integer limit,
                            @RequestBody(required = false) CourseQuery courseQuery){
        return courseService.getPageCourse(page,limit,courseQuery);
    }
    @DeleteMapping("/{id}")
    public R deleteCourse(@PathVariable String id){
        return courseService.deleteCourseById(id);
    }

    @ApiOperation("根据课程id获取课程信息")
    @GetMapping("/vo/{id}")
    public EduCourseVo getCourseVoById(@PathVariable String id){
        EduCourseVo eduCourseVo = new EduCourseVo();
        BeanUtils.copyProperties(courseService.getById(id), eduCourseVo);
        return eduCourseVo;
    }
}
