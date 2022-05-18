package com.anyi.serviceedu.controller.front;


import com.anyi.commonutils.R;
import com.anyi.commonutils.utils.JwtUtils;
import com.anyi.commonutils.vo.UcenterMemberVo;
import com.anyi.serviceedu.entity.UcenterMember;
import com.anyi.serviceedu.entity.vo.LoginVo;
import com.anyi.serviceedu.entity.vo.RegisterVo;
import com.anyi.serviceedu.entity.vo.UserVo;
import com.anyi.serviceedu.service.EduCourseService;
import com.anyi.serviceedu.service.EduTeacherService;
import com.anyi.serviceedu.service.UCenterMemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author anyi
 * @since 2022-05-16
 */
@Api(tags = "前台用户管理")
@RestController
@CrossOrigin
@Slf4j
@RequestMapping("/edu/member")
public class UCenterMemberController {

    @Resource
    private HttpServletRequest request;
    @Resource
    private UCenterMemberService uCenterMemberService;

    @Resource
    private EduTeacherService eduTeacherService;

    @Resource
    private EduCourseService eduCourseService;

    @ApiOperation("用户登录")
    @PostMapping("/login")
    public R userLogin(@RequestBody LoginVo loginVo){
        String token = uCenterMemberService.login(loginVo);
        return R.ok().data("token",token);
    }

    // 用户注册
    @ApiOperation("用户注册")
    @PostMapping("/register")
    public R userRegister(@RequestBody RegisterVo registerVo){
        return uCenterMemberService.register(registerVo);
    }

    @GetMapping("/getCode/{mobile}")
    // 发送验证码，这里由于阿里云添加标签失败，所以直接存入redis中，我自己查看验证码
    public R getCode(@PathVariable String mobile){
        return uCenterMemberService.getCode(mobile);
    }
    @GetMapping("/getInfo")
    public R getUserInfo(){
        String id = JwtUtils.getMemberIdByJwtToken(request);
        UcenterMember info = uCenterMemberService.getById(id);
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(info, userVo);
        return R.ok().data("item",userVo);
    }
    @ApiOperation("根据id查询讲师")
    @GetMapping("/user/vo/{id}")
    public UcenterMemberVo getUserVoById(@PathVariable String id){
        UcenterMemberVo ucenterMemberVo = new UcenterMemberVo();

        UcenterMember teacher = uCenterMemberService.getById(id);
        BeanUtils.copyProperties(teacher, ucenterMemberVo);
        return ucenterMemberVo;
    }
    // 根据日期获取当天的注册人数
    @GetMapping("/registerNum/{date}")
    public Integer getRegisterNum(@PathVariable String date){
        return uCenterMemberService.getRegisterNum(date);
    }

}

