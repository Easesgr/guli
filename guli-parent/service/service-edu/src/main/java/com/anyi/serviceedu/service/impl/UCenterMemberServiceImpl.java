package com.anyi.serviceedu.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.RandomUtil;
import com.anyi.commonutils.R;
import com.anyi.commonutils.ResultCode;
import com.anyi.commonutils.utils.JwtUtils;
import com.anyi.commonutils.utils.MD5;
import com.anyi.servicebase.exception.GuliException;
import com.anyi.serviceedu.entity.UcenterMember;
import com.anyi.serviceedu.entity.vo.LoginVo;
import com.anyi.serviceedu.entity.vo.RegisterVo;
import com.anyi.serviceedu.mapper.UcenterMemberMapper;
import com.anyi.serviceedu.service.UCenterMemberService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.BeanUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author anyi
 * @since 2022-05-16
 */
@Service
public class UCenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UCenterMemberService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;
    // 登录
    @Override
    public String login(LoginVo loginVo) {
        // 判断是否为空
        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)){
            throw new GuliException(ResultCode.ERROR,"登录失败");
        }
        // 查询数据库是否该用户
        UcenterMember exist = getOne(new QueryWrapper<UcenterMember>().eq("mobile", mobile));
        if (exist == null) {
            throw new GuliException(ResultCode.ERROR,"用户不存在，请先注册");
        }
        // 判断密码是否错误
        if (!MD5.encrypt(password).equals(exist.getPassword())){
            throw new GuliException(ResultCode.ERROR,"账号或密码错误！");
        }
        // 判断是否是已经禁用的用户

        if (exist.getIsDisabled()){
            throw new GuliException(ResultCode.ERROR,"用户已经禁用");
        }
        // 生成token
        String jwtToken = JwtUtils.getJwtToken(exist.getId(), exist.getNickname());
        // 返回
        return jwtToken;
    }

    // 用户注册
    @Override
    public R register(RegisterVo registerVo) {
        String code = registerVo.getCode();
        String mobile = registerVo.getMobile();
        String nickname = registerVo.getNickname();
        String password = registerVo.getPassword();
        // 判断数据是否为空
        if (StringUtils.isEmpty(code) || StringUtils.isEmpty(mobile)
                || StringUtils.isEmpty(nickname) || StringUtils.isEmpty(password)){
            throw new GuliException(ResultCode.ERROR,"信息不能为空");
        }
        // 查询数据库是否已经存在
        UcenterMember exist = getOne(new QueryWrapper<UcenterMember>().eq("mobile", mobile));
        if (exist != null){
            throw new GuliException(ResultCode.ERROR,"用户已经存在，不能重复注册");
        }
        // 取出redis中的 code 判断是否和输入的一致
        String redisCode = stringRedisTemplate.opsForValue().get(mobile);

        if (!redisCode.equals(code)){
            throw new GuliException(ResultCode.ERROR,"验证码错误");
        }
        UcenterMember ucenterMember = new UcenterMember();
        BeanUtil.copyProperties(registerVo,ucenterMember);
        ucenterMember.setPassword(MD5.encrypt(password));
        ucenterMember.setAvatar("https://edu-longyang.oss-cn-beijing.aliyuncs.com/fa104ef58c4e5bc4270d911da1d1b34d.jpg");
        boolean save = save(ucenterMember);
        if (save){
            return R.ok();
        }else {
            return R.error();
        }
    }

    // 获取验证码
    @Override
    public R getCode(String mobile) {
        // 生成一个随机的6位数的验证码
        String code = RandomUtil.randomNumbers(6);
        // 将验证码存到redis中
        stringRedisTemplate.opsForValue().set(mobile, code,3, TimeUnit.MINUTES);
        return R.ok().data("code",code);
    }
    // 根据日期获取当天的注册人数
    @Override
    public Integer getRegisterNum(String date) {
        Integer registerNum = baseMapper.getRegisterNum(date);
        return registerNum;
    }
}
