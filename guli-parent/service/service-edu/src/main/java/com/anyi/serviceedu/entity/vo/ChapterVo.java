package com.anyi.serviceedu.entity.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 安逸i
 * @version 1.0
 */
@Data
public class ChapterVo {
    private static final long serialVersionUID = 1L;
    private String id;
    private String title;
    private List<VideoVo> children = new ArrayList<>();
}
