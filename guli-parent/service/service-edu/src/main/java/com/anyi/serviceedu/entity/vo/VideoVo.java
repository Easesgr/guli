package com.anyi.serviceedu.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 安逸i
 * @version 1.0
 */
@Data
public class VideoVo {
    private static final long serialVersionUID = 1L;
    private String id;
    private String title;
    private Boolean free;
    @ApiModelProperty(value = "云端视频资源")
    private String videoSourceId;
}
