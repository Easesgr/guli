package com.anyi.serviceedu.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.read.listener.ReadListener;
import com.anyi.serviceedu.entity.EduSubject;
import com.anyi.serviceedu.entity.excel.ReadData;
import com.anyi.serviceedu.service.EduSubjectService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.util.Map;

/**
 * @author 安逸i
 * @version 1.0
 */
public class ExcelListener extends AnalysisEventListener<ReadData> {

    public EduSubjectService subjectService;
    public ExcelListener() {

    }
    public ExcelListener(EduSubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @Override
    public void invoke(ReadData readData, AnalysisContext analysisContext) {
        System.out.println(readData);
        EduSubject eduSubject = new EduSubject();
        // 一级分类
        EduSubject exist= existSubject("0", readData.getOneSubject());
        if (exist==null){
            eduSubject.setTitle(readData.getOneSubject());
            eduSubject.setParentId("0");
            subjectService.save(eduSubject);
        }else {
            eduSubject = exist;
        }
        // 二级分类
        EduSubject twoSubject = new EduSubject();

        if (existSubject(eduSubject.getId(),readData.getTwoSubject()) == null){
            twoSubject.setTitle(readData.getTwoSubject());
            twoSubject.setParentId(eduSubject.getId());
            subjectService.save(twoSubject);
        }
    }

    public EduSubject existSubject(String parentId,String title){
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id", parentId);
        wrapper.eq("title", title);
        return subjectService.getOne(wrapper);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
