package com.anyi.serviceedu.service.impl;

import com.anyi.commonutils.R;
import com.anyi.commonutils.ResultCode;
import com.anyi.servicebase.exception.GuliException;
import com.anyi.serviceedu.entity.EduChapter;
import com.anyi.serviceedu.entity.EduVideo;
import com.anyi.serviceedu.entity.vo.ChapterVo;
import com.anyi.serviceedu.entity.vo.CourseVo;
import com.anyi.serviceedu.entity.vo.VideoVo;
import com.anyi.serviceedu.mapper.EduChapterMapper;
import com.anyi.serviceedu.service.EduChapterService;
import com.anyi.serviceedu.service.EduVideoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author anyi
 * @since 2022-05-14
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {
    @Resource
    private EduVideoService videoService;
    // 获取章节和小结信息
    @Override
    public R getChapterAndVideo(String courseId) {
        // 1. 获取章节信息
        List<ChapterVo> chapterVos = new ArrayList<>();
        QueryWrapper<EduChapter> wrapper= new QueryWrapper<>();
        wrapper.eq("course_id", courseId);
        wrapper.orderByAsc("sort");

        List<EduChapter> chapters = list(wrapper);
        for (EduChapter chapter : chapters) {
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(chapter, chapterVo);
            List<VideoVo> children = new ArrayList<>();
            // 2. 根据章节id 获取小结信息
            List<EduVideo> videos = videoService.list(new QueryWrapper<EduVideo>().eq("chapter_id", chapter.getId()));
            for (EduVideo video : videos) {
                VideoVo videoVo = new VideoVo();
                BeanUtils.copyProperties(video, videoVo);
                children.add(videoVo);
            }
            chapterVo.setChildren(children);
            chapterVos.add(chapterVo);
        }
        // 3. 返回数据
        return R.ok().data("list",chapterVos);
    }
    // 删除章节
    @Override
    public R delete(String chapterId) {
        // 1.根据 章节id 查询是否有小结
        int count = videoService.count(new QueryWrapper<EduVideo>().eq("chapter_id", chapterId));
        if (count>0){
            return R.error().message("该章节有小结，不能删除！");
        }
        boolean flag = removeById(chapterId);
        if(!flag){
           throw new GuliException(ResultCode.ERROR,"删除章节失败!");
        }
        return R.ok();
    }
}
