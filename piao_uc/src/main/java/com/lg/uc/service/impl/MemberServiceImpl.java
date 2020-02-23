package com.lg.uc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lg.uc.entity.Member;
import com.lg.uc.mapper.MemberMapper;
import com.lg.uc.service.MemberService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author lg
 * @since 2020-02-11
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {


    @Override
    public Integer countRegisterByDay(String day) {
        return baseMapper.selectRegisterCount(day);
    }

    @Override
    public Member getByOpenid(String openid) {

        QueryWrapper<Member> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("openid", openid);

        return baseMapper.selectOne(queryWrapper);
    }
}
