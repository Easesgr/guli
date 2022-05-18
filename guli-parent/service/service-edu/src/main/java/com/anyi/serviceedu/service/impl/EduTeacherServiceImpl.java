package com.anyi.serviceedu.service.impl;

import com.anyi.commonutils.R;
import com.anyi.serviceedu.entity.EduTeacher;
import com.anyi.serviceedu.mapper.EduTeacherMapper;
import com.anyi.serviceedu.entity.query.TeacherQuery;
import com.anyi.serviceedu.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author anyi
 * @since 2022-05-12
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {
    @Override
    public R getConditionTeacher(Long page, Long limit, TeacherQuery teacherQuery) {
        Page<EduTeacher> pageList = new Page<>(page, limit);
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();

        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        Integer level = teacherQuery.getLevel();
        String name = teacherQuery.getName();

        if (!StringUtils.isEmpty(begin)){
            wrapper.gt("gmt_create",begin);
        }
        if (!StringUtils.isEmpty(end)){
            wrapper.lt("gmt_modified",end);
        }
        if (!StringUtils.isEmpty(name)){
            wrapper.like("name",name);
        }
        if (level != null ){
            wrapper.eq("level",level);
        }
        wrapper.orderByDesc("gmt_modified");
        page(pageList,wrapper);
        return R.ok().data("total", pageList.getTotal()).data("items", pageList.getRecords());
    }
}
