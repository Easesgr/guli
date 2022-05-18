package com.anyi.serviceedu.controller;


import com.anyi.commonutils.R;
import com.anyi.commonutils.ResultCode;
import com.anyi.servicebase.exception.GuliException;
import com.anyi.serviceedu.entity.EduChapter;
import com.anyi.serviceedu.entity.vo.CourseVo;
import com.anyi.serviceedu.service.EduChapterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

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
@Api(tags = "章节管理")
@RequestMapping("/edu/chapter")
public class EduChapterController {
    @Resource
    private EduChapterService eduChapterService;

    @ApiOperation("根据id获取course信息")
    @GetMapping("/getChapterAndVideo/{courseId}")
    public R getChapterAndVideo(@PathVariable String  courseId){
        return eduChapterService.getChapterAndVideo(courseId);
    }
    // 添加章节
    @ApiOperation("添加章节")
    @PostMapping("/add")
    public R addChapter(@RequestBody EduChapter eduChapter){
        boolean save = eduChapterService.save(eduChapter);
        if (!save){
            return R.error().message("添加失败！");
        }
        return R.ok().message("添加成功");
    }

    // 删除章节
    @ApiOperation("删除章节")
    @DeleteMapping("/{chapterId}")
    public R delete(@PathVariable String chapterId){
        return eduChapterService.delete(chapterId);
    }
    // 更新章节
    @ApiOperation("更新章节")
    @PutMapping("/update")
    public R updateChapter(@RequestBody EduChapter eduChapter){
        boolean b = eduChapterService.saveOrUpdate(eduChapter);
        if (!b){
            throw new GuliException(ResultCode.ERROR,"更新失败");
        }
        return R.ok();
    }

    // 根据id获取章节
    @ApiOperation("根据id获取chapter")
    @GetMapping("/{chapterId}")
    public R getChapterById(@PathVariable String chapterId){
        EduChapter chapter = eduChapterService.getById(chapterId);
        return R.ok().data("chapter",chapter);
    }
}

