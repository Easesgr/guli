package com.anyi.serviceedu.service;

import com.anyi.commonutils.R;
import com.anyi.serviceedu.entity.UcenterMember;
import com.anyi.serviceedu.entity.vo.LoginVo;
import com.anyi.serviceedu.entity.vo.RegisterVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author anyi
 * @since 2022-05-16
 */
public interface UCenterMemberService extends IService<UcenterMember> {

    // 登录
    String login(LoginVo loginVo);
    // 用户注册
    R register(RegisterVo registerVo);

    // 获取验证码
    R getCode(String mobile);
    // 根据日期获取当天的注册人数
    Integer getRegisterNum(String date);
}
