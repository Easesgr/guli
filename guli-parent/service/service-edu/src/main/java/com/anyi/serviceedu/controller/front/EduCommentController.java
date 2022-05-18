package com.anyi.serviceedu.controller.front;


import com.anyi.commonutils.R;
import com.anyi.commonutils.utils.JwtUtils;
import com.anyi.serviceedu.entity.EduComment;
import com.anyi.serviceedu.entity.EduTeacher;
import com.anyi.serviceedu.entity.UcenterMember;
import com.anyi.serviceedu.entity.vo.CommentVo;
import com.anyi.serviceedu.entity.vo.CourseVo;
import com.anyi.serviceedu.service.EduCommentService;

import com.anyi.serviceedu.service.UCenterMemberService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mysql.cj.Query;
import io.swagger.annotations.Api;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 评论 前端控制器
 * </p>
 *
 * @author anyi
 * @since 2022-05-17
 */
@Api(tags = "评论管理")
@RestController
@RequestMapping("/edu/comment")
@CrossOrigin
public class EduCommentController {

    @Resource
    private EduCommentService eduCommentService;


    @Resource
    private UCenterMemberService uCenterMemberService;

    // 分页查询数据
    @GetMapping("getList/{page}/{limit}")
    public R getCommentList(@PathVariable Integer page, @PathVariable Integer limit){
        Page<EduComment> pageParam = new Page<>(page,limit);
        QueryWrapper<EduComment> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("gmt_create");
        eduCommentService.page(pageParam, wrapper);
        List<EduComment> records = pageParam.getRecords();
        long current = pageParam.getCurrent();
        long pages = pageParam.getPages();
        long size = pageParam.getSize();
        long total = pageParam.getTotal();
        boolean hasNext = pageParam.hasNext();
        boolean hasPrevious = pageParam.hasPrevious();
        Map<String, Object> map = new HashMap<>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);

        return R.ok().data(map);
    }
    // 发布评论
    @PostMapping("/publishComment")
    public R publishComment(@RequestBody CommentVo courseVo, HttpServletRequest request){
        // 获取用户信息
        String userId = JwtUtils.getMemberIdByJwtToken(request);
        // 根据用户id查询出用户信息
        UcenterMember user = uCenterMemberService.getById(userId);
        EduComment eduComment = new EduComment();
        // 将信息复制到comment中
        BeanUtils.copyProperties(user, eduComment);
        BeanUtils.copyProperties(courseVo, eduComment);
        eduComment.setId(null);
        eduCommentService.save(eduComment);
        return R.ok();
    }

}

