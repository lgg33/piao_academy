package com.lg.uc.controller;


import com.lg.common.response.ResponseResult;
import com.lg.uc.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author lg
 * @since 2020-02-11
 */
@Api(tags = "用户管理")
@RestController
@RequestMapping("/admin/uc/member")
@CrossOrigin
public class MemberController {

    @Autowired
    MemberService memberService;

    @ApiOperation("注册信息")
    @GetMapping("/{day}")
    public ResponseResult selectRegister(@PathVariable("day") String day) {
        Integer registerNum = memberService.countRegisterByDay(day);
        return ResponseResult.success().data("item", registerNum);
    }
}

