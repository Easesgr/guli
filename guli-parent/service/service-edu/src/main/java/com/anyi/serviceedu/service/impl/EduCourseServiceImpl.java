package com.anyi.serviceedu.service.impl;


import com.anyi.commonutils.R;
import com.anyi.commonutils.ResultCode;
import com.anyi.servicebase.exception.GuliException;
import com.anyi.serviceedu.entity.EduChapter;
import com.anyi.serviceedu.entity.EduCourse;
import com.anyi.serviceedu.entity.EduCourseDescription;
import com.anyi.serviceedu.entity.EduVideo;
import com.anyi.serviceedu.entity.query.CourseFrontQuery;
import com.anyi.serviceedu.entity.query.CourseQuery;
import com.anyi.serviceedu.entity.vo.ChapterVo;
import com.anyi.serviceedu.entity.vo.CoursePublishVo;
import com.anyi.serviceedu.entity.vo.CourseVo;
import com.anyi.serviceedu.entity.vo.CourseWebVo;
import com.anyi.serviceedu.mapper.EduCourseMapper;
import com.anyi.serviceedu.service.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author anyi
 * @since 2022-05-14
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Resource
    private EduCourseDescriptionService descriptionService;

    @Resource
    private EduVideoService videoService;

    @Resource
    private EduChapterService chapterService;
    @Resource
    private VodService vodService;


    @Override
    public R addCourseInfo(CourseVo courseVo) {
        // 1. 保存课程信息
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseVo, eduCourse);
        boolean flag = save(eduCourse);
        if (!flag){
            throw new GuliException(ResultCode.ERROR,"添加课程失败");
        }

        // 2. 带上课程id保存课程简介
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        BeanUtils.copyProperties(courseVo, eduCourseDescription);
        eduCourseDescription.setId(eduCourse.getId());
        descriptionService.save(eduCourseDescription);
        // 3. 返回课程id
        return R.ok().data("courseId",eduCourse.getId());
    }
    // 根据课程id获取课程信息
    @Override
    public R getCourseById(String courseId) {
        // 1.获取course信息
        EduCourse course = getById(courseId);

        // 2. 获取course 描述信息
        EduCourseDescription description = descriptionService.getById(courseId);
        // 3. 封装称vo对象，返回信息
        CourseVo courseVo = new CourseVo();
        BeanUtils.copyProperties(course, courseVo);

        courseVo.setDescription(description.getDescription());

        return R.ok().data("courseVo",courseVo);
    }

    // 更新course信息
    @Override
    public R updateCourse(CourseVo courseVo) {
        // 1. 保存课程信息
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseVo, eduCourse);
        boolean flag = saveOrUpdate(eduCourse);
        if (!flag){
            throw new GuliException(ResultCode.ERROR,"修改课程失败");
        }

        // 2. 带上课程id保存课程简介
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        BeanUtils.copyProperties(courseVo, eduCourseDescription);
        eduCourseDescription.setId(eduCourse.getId());
        descriptionService.saveOrUpdate(eduCourseDescription);
        // 3. 返回课程id
        return R.ok().data("courseId",eduCourse.getId());
    }
    // 获取发布课程信息
    @Override
    public R getPublishCourseInfo(String id) {
        CoursePublishVo coursePublishVo = baseMapper.getPublishCourseInfo(id);
        return R.ok().data("publishCourse",coursePublishVo);
    }


    // 根据id删除课程
    @Override
    public R deleteCourseById(String id) {

        // 删除小节中的视频
        // 根据id 查出所以该课程的小节视频
        QueryWrapper<EduVideo> videoQueryWrapper = new QueryWrapper<>();
        videoQueryWrapper.select("video_source_id");
        videoQueryWrapper.eq("course_id", id);
        List<String > idList = new ArrayList<>();
        List<EduVideo> videoList = videoService.list(videoQueryWrapper);
        if (videoList.size() == 0){
            throw new GuliException(ResultCode.ERROR,"没有课程视频删除失败");
        }
        for (EduVideo video : videoList) {
            idList.add(video.getVideoSourceId());
        }

        vodService.batchDeleteVideo(idList);

        // 删除小节
        boolean videoFlag = videoService.remove(new QueryWrapper<EduVideo>().eq("course_id", id));
        if (!videoFlag){
            throw new GuliException(ResultCode.ERROR,"删除小节失败");
        }
        // 删除章节
        boolean chapterFlag = chapterService.remove(new QueryWrapper<EduChapter>().eq("course_id", id));
        if (!chapterFlag){
            throw new GuliException(ResultCode.ERROR,"删除章节失败");
        }
        // 删除描述
        descriptionService.removeById(id);
        // 删除课程
        boolean b = removeById(id);
        if (!b){
            throw new GuliException(ResultCode.ERROR,"删除课程失败");
        }
        // 返回结果
        return R.ok();
    }
    // 分页查询和条件查询课程
    @Override
    public R getPageCourse(Integer page, Integer limit, CourseQuery courseQuery) {
        Page<EduCourse> pageCondition = new Page<>(page, limit);

        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        String subjectId = courseQuery.getSubjectId();
        String subjectParentId = courseQuery.getSubjectParentId();
        String teacherId = courseQuery.getTeacherId();
        String title = courseQuery.getTitle();
        if (!StringUtils.isEmpty(subjectId )){
            wrapper.eq("subject_id", subjectId);
        }
        if (!StringUtils.isEmpty(subjectParentId)){
            wrapper.eq("subject_parent_id", subjectParentId);
        }
        if (!StringUtils.isEmpty(teacherId)){
            wrapper.eq("teacher_id", teacherId);
        }
        if (!StringUtils.isEmpty(title)){
            wrapper.eq("title", title);
        }
        wrapper.orderByDesc("gmt_create");
        IPage<EduCourse> pageInfo = page(pageCondition, wrapper);
        return R.ok().data("total",pageInfo.getTotal()).data("courses",pageInfo.getRecords());
    }

    // 分页查询课程信息
    @Override
    public R getFrontCourse(Integer page, Integer limit, CourseFrontQuery courseQuery) {
        Page<EduCourse> pageParam = new Page<>(page,limit);
        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(courseQuery.getSubjectParentId())) {
            queryWrapper.eq("subject_parent_id",
                    courseQuery.getSubjectParentId());
        }
        if (!StringUtils.isEmpty(courseQuery.getSubjectId())) {
            queryWrapper.eq("subject_id", courseQuery.getSubjectId());
        }
        if (!StringUtils.isEmpty(courseQuery.getBuyCountSort())) {
            queryWrapper.orderByDesc("buy_count");
        }
        if (!StringUtils.isEmpty(courseQuery.getGmtCreateSort())) {
            queryWrapper.orderByDesc("gmt_create");
        }
        if (!StringUtils.isEmpty(courseQuery.getPriceSort())) {
            queryWrapper.orderByDesc("price");
        }
        baseMapper.selectPage(pageParam, queryWrapper);
        List<EduCourse> records = pageParam.getRecords();
        long current = pageParam.getCurrent();
        long pages = pageParam.getPages();
        long size = pageParam.getSize();
        long total = pageParam.getTotal();
        boolean hasNext = pageParam.hasNext();
        boolean hasPrevious = pageParam.hasPrevious();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);

        return R.ok().data(map);
    }
    // 根据id查询课程的详情信息
    @Override
    public R getDetailInfo(String id) {
        // 查询出课程和教师相关信息
        CourseWebVo courseWebVo = baseMapper.selectInfoWebById(id);
        // 章节和小节信息
        List<ChapterVo> chapterVoList = (List<ChapterVo>) chapterService.getChapterAndVideo(id).getData().get("list");
        return R.ok().data("courseInfo",courseWebVo).data("chapterAndVideoList",chapterVoList);
    }
}
