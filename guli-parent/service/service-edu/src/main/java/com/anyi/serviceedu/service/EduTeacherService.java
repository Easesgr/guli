package com.anyi.serviceedu.service;

import com.anyi.commonutils.R;
import com.anyi.serviceedu.entity.EduTeacher;
import com.anyi.serviceedu.entity.query.TeacherQuery;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author anyi
 * @since 2022-05-12
 */
public interface EduTeacherService extends IService<EduTeacher> {
     R getConditionTeacher(Long page, Long limit, TeacherQuery teacherQuery);
}
