package com.anyi.serviceedu.service;

import com.anyi.commonutils.R;
import com.anyi.serviceedu.entity.EduChapter;
import com.anyi.serviceedu.entity.vo.CourseVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author anyi
 * @since 2022-05-14
 */
public interface EduChapterService extends IService<EduChapter> {
    // 获取章节和小结信息
    R getChapterAndVideo(String courseId);
    // 删除章节
    R delete(String chapterId);
}
