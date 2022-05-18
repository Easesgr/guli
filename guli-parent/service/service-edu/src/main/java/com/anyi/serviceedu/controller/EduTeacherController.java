package com.anyi.serviceedu.controller;


import com.anyi.serviceedu.entity.EduTeacher;
import com.anyi.serviceedu.entity.query.TeacherQuery;
import com.anyi.serviceedu.service.EduTeacherService;
import com.anyi.commonutils.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author anyi
 * @since 2022-05-12
 */
@Api(tags = "讲师管理")
@RestController
@RequestMapping("/edu/teacher")
@CrossOrigin
public class EduTeacherController {
    @Resource
    private EduTeacherService eduTeacherService;

    @ApiOperation("所有讲师")
    @GetMapping("/findAll")
    public R list(){
        List<EduTeacher> list = eduTeacherService.list(null);
        return R.ok().data("items",list);
    }
    @ApiOperation("删除讲师")
    @DeleteMapping("{id}")
    public R removeById(@ApiParam("讲师id")@PathVariable String id){
        boolean b = eduTeacherService.removeById(id);
        if (b){
            return R.ok();
        }else {
            return R.error();
        }
    }
    // 分页查询
    @ApiOperation("讲师分页列表")
    @GetMapping("/{page}/{limit}")
    public R pageList(@ApiParam("当前页")@PathVariable Long page,
                      @ApiParam("每页记录数")@PathVariable Long limit){
        Page<EduTeacher> pageList = new Page<EduTeacher>(page, limit);
        eduTeacherService.page(pageList, null);
        return R.ok().data("total",pageList.getTotal()).data("items",pageList.getRecords());
    }

    @ApiOperation("多条件分页查询")
    @PostMapping("/condition/{page}/{limit}")
    public R condition(@ApiParam(value = "当前页",required = true)@PathVariable Long page,
                       @ApiParam(value = "每页记录数",required = true)@PathVariable Long limit,
                       @RequestBody(required = false) TeacherQuery teacherQuery){

        return eduTeacherService.getConditionTeacher(page, limit, teacherQuery);
    }
    @ApiOperation("添加讲师")
    @PostMapping("/create")
    public R create(@RequestBody EduTeacher eduTeacher){
        boolean flag = eduTeacherService.save(eduTeacher);
        if (flag){
            return R.ok();
        }else {
            return R.error();
        }
    }

    @ApiOperation("根据id查询讲师")
    @GetMapping("/{id}")
    public R getById(@PathVariable String id){
        EduTeacher teacher = eduTeacherService.getById(id);
        return R.ok().data("data",teacher);
    }

    @ApiOperation("更新讲师")
    @PostMapping("/update")
    public R updateById(@RequestBody(required = false) EduTeacher eduTeacher){
        boolean b = eduTeacherService.updateById(eduTeacher);
        if (b){
            return R.ok();
        }else {
            return R.error();
        }
    }
}

