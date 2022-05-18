package com.anyi.serviceedu.service;

import com.anyi.commonutils.R;
import com.anyi.serviceedu.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author anyi
 * @since 2022-05-14
 */
public interface EduSubjectService extends IService<EduSubject> {

    void uploadExcel(MultipartFile file, EduSubjectService subjectService);

    R getList();
}
