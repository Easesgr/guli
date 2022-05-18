package com.anyi.serviceedu.controller.front;

import com.anyi.commonutils.R;
import com.anyi.serviceedu.entity.EduCourse;
import com.anyi.serviceedu.entity.EduTeacher;
import com.anyi.serviceedu.service.EduCourseService;
import com.anyi.serviceedu.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.redis.hash.HashMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 安逸i
 * @version 1.0
 */
@Api(tags = "前台讲师课程管理")
@RestController
@CrossOrigin
@RequestMapping("/edu/front")
public class TeacherAndCourseController {
    @Resource
    private EduCourseService eduCourseService;
    @Resource
    private EduTeacherService eduTeacherService;

    @GetMapping("/getHotTeacherAndCourse")
    @ApiOperation("获取热门教师和课程")
    public R getHotTeacherAndCourse(){
        // 获取热门课程
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.last("limit 8");
        List<EduCourse> courseList = eduCourseService.list(wrapper);
        // 获取热门教师
        QueryWrapper<EduTeacher> wrapper1 = new QueryWrapper<>();
        wrapper1.last("limit 4");
        List<EduTeacher> teacherList = eduTeacherService.list(wrapper1);
        // 返回数据
        return R.ok().data("courseList",courseList).data("teacherList",teacherList);
    }
    // 前台数据返回
    @GetMapping("/teacher/{page}/{limit}")
    public R getTeacherList(@PathVariable Integer page, @PathVariable Integer limit){
        Page<EduTeacher> pageParam = new Page<>(page,limit);
        QueryWrapper<EduTeacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("sort");
        eduTeacherService.page(pageParam, queryWrapper);
        List<EduTeacher> records = pageParam.getRecords();
        long current = pageParam.getCurrent();
        long pages = pageParam.getPages();
        long size = pageParam.getSize();
        long total = pageParam.getTotal();
        boolean hasNext = pageParam.hasNext();
        boolean hasPrevious = pageParam.hasPrevious();
        Map<String, Object> map = new HashMap<>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);
        return R.ok().data(map);
    }
    // 根据id查询讲师信息，并且将讲师的课程信息查出来
    @GetMapping("/teacherAndCourse/{id}")
    public R getTeacherAndCourse(@PathVariable String  id){
        EduTeacher teacherInfo = eduTeacherService.getById(id);
        // 根据id查询所有的课程信息
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.eq("teacher_id", id);
        wrapper.orderByDesc("gmt_modified");
        List<EduCourse> list = eduCourseService.list(wrapper);
        return R.ok().data("teacherInfo",teacherInfo).data("courseList",list);
    }
}
