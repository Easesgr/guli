package com.anyi.serviceedu.entity.query;

import lombok.Data;

/**
 * @author 安逸i
 * @version 1.0
 */
@Data
public class CourseQuery {
    // 一级分类
    private String subjectParentId;

    // 二级分类
    private String subjectId;

    private String title;
    // 讲师id
    private String teacherId;
}
