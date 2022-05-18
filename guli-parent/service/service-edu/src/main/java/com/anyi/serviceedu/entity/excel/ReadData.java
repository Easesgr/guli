package com.anyi.serviceedu.entity.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @author 安逸i
 * @version 1.0
 */
@Data
public class ReadData {
    @ExcelProperty(index = 0 ,value = "分类一")
    private String  oneSubject;
    @ExcelProperty(index = 1,value = "分类二")
    private String twoSubject;
}
