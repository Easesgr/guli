package com.anyi.serviceedu.service.impl;

import ch.qos.logback.core.joran.util.beans.BeanUtil;
import com.alibaba.excel.EasyExcel;
import com.anyi.commonutils.R;
import com.anyi.serviceedu.entity.EduSubject;
import com.anyi.serviceedu.entity.excel.ReadData;
import com.anyi.serviceedu.entity.subject.OneSubject;
import com.anyi.serviceedu.entity.subject.TwoSubject;
import com.anyi.serviceedu.listener.ExcelListener;
import com.anyi.serviceedu.mapper.EduSubjectMapper;
import com.anyi.serviceedu.service.EduSubjectService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author anyi
 * @since 2022-05-14
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    @Override
    public void uploadExcel(MultipartFile file, EduSubjectService subjectService) {
        InputStream in = null;
        try {
            in = file.getInputStream();
            // 调用read方法，真正执行在listener
            EasyExcel.read(in, ReadData.class, new ExcelListener(subjectService)).sheet().doRead();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public R getList() {
        // 1. 查出所有一级分类
        List<OneSubject> finalList = new ArrayList<>();

        List<EduSubject> oneList = list(new QueryWrapper<EduSubject>().eq("parent_id", "0"));
        for (EduSubject eduSubject : oneList) {
            OneSubject oneSubject = new OneSubject();
            // 2. 封装一级分类
            BeanUtils.copyProperties(eduSubject, oneSubject);

            // 3. 根据一级分类将二级分类查出

            List<EduSubject> childrenList = list(new QueryWrapper<EduSubject>().eq("parent_id", eduSubject.getId()));

            List<TwoSubject> twoList  = new ArrayList<>();
            for (EduSubject subject : childrenList) {
                TwoSubject twoSubject = new TwoSubject();
                // 4. 封装二级分类
                BeanUtils.copyProperties(subject, twoSubject);
                twoList.add(twoSubject);
            }
            oneSubject.setChildren(twoList);
            finalList.add(oneSubject);
        }
        // 5. 返回结果
        return R.ok().data("list",finalList);
    }
}
