package com.lg.uc.mapper;

import com.lg.uc.entity.Member;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author lg
 * @since 2020-02-11
 */
public interface MemberMapper extends BaseMapper<Member> {
    Integer selectRegisterCount(String day);
}
