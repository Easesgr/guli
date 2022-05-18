package com.anyi.serviceedu.mapper;

import com.anyi.serviceedu.entity.EduCourse;
import com.anyi.serviceedu.entity.vo.CoursePublishVo;
import com.anyi.serviceedu.entity.vo.CourseWebVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author anyi
 * @since 2022-05-14
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {

    CoursePublishVo getPublishCourseInfo(@Param("id") String id);

    CourseWebVo selectInfoWebById(@Param("id") String id);
}
