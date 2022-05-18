package com.anyi.serviceedu.controller;


import com.anyi.commonutils.R;
import com.anyi.serviceedu.service.EduSubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author anyi
 * @since 2022-05-14
 */
@Api(tags = "课程分类管理")
@RestController
@CrossOrigin
@RequestMapping("/edu/subject")
public class EduSubjectController {

    @Resource
    EduSubjectService subjectService;

    @ApiOperation("上传excel")
    @PostMapping("excel")
    public R uploadExcel(MultipartFile file){
        if (file ==null){
            return R.error().message("您没有上传文件");
        }
        subjectService.uploadExcel(file,subjectService);
        return R.ok();
    }
    @ApiOperation("分类列表")
    @GetMapping("list")
    public R getList(){
        return subjectService.getList();
    }
}

