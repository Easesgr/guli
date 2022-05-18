package com.anyi.serviceedu.service;

import com.anyi.commonutils.R;
import com.anyi.serviceedu.entity.EduCourse;
import com.anyi.serviceedu.entity.query.CourseFrontQuery;
import com.anyi.serviceedu.entity.query.CourseQuery;
import com.anyi.serviceedu.entity.vo.CourseVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author anyi
 * @since 2022-05-14
 */
public interface EduCourseService extends IService<EduCourse> {
    // 添加课程信息
    R addCourseInfo(CourseVo courseVo);

    // 根据课程id获取课程信息
    R getCourseById(String courseId);
    // 更新course信息
    R updateCourse(CourseVo courseVo);
    // 获取发布课程信息
    R getPublishCourseInfo(String id);
    // 根据id删除课程
    R deleteCourseById(String id);
    // 分页查询和条件查询课程
    R getPageCourse(Integer page, Integer limit, CourseQuery courseQuery);

    // 分页查询课程信息
    R getFrontCourse(Integer page, Integer limit, CourseFrontQuery courseVo);
    // 根据id查询课程的详情信息
    R getDetailInfo(String id);
}
