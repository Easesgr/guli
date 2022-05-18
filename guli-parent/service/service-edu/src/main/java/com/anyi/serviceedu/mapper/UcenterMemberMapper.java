package com.anyi.serviceedu.mapper;

import com.anyi.serviceedu.entity.UcenterMember;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author anyi
 * @since 2022-05-16
 */
public interface UcenterMemberMapper extends BaseMapper<UcenterMember> {
    Integer getRegisterNum(@Param("date") String date);
}
