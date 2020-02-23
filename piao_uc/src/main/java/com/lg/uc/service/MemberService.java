package com.lg.uc.service;

import com.lg.uc.entity.Member;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author lg
 * @since 2020-02-11
 */
public interface MemberService extends IService<Member> {
    Integer countRegisterByDay(String day);

    Member getByOpenid(String openid);

}
