package com.anyi.serviceedu.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 安逸i
 * @version 1.0
 */
@Data
public class CoursePublishVo {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "课程标题")
    private String title;

    @ApiModelProperty(value = "封面")
    private String cover;

    @ApiModelProperty(value = "课程时长")
    private Integer lessonNum;

    @ApiModelProperty(value = "课程一级标题")
    private String subjectLevelOne;

    @ApiModelProperty(value = "课程二级标题")
    private String subjectLevelTwo;

    @ApiModelProperty(value = "讲师名字")
    private String teacherName;

    @ApiModelProperty(value = "课程价格")
    private String price;//只用于显示
}
