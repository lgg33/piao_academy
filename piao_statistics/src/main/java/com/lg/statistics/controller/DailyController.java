package com.lg.statistics.controller;


import com.lg.common.response.ResponseResult;
import com.lg.statistics.service.DailyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author lg
 * @since 2020-02-11
 */
@Api(tags = "统计中心")
@RestController
@CrossOrigin
@RequestMapping("/admin/statistics/daily")
public class DailyController {

    @Autowired
    private DailyService dailyService;

    @ApiOperation("生成数据")
    @PostMapping("/{day}")
    public ResponseResult getStatisticsData(@PathVariable("day") String day) {
        dailyService.createStatisticsByDay(day);
        return ResponseResult.success();
    }

    @ApiOperation("按类型统计")
    @GetMapping("/{begin}/{end}/{type}")
    public ResponseResult showChart(@PathVariable("begin") String begin,
                                    @PathVariable("end") String end, @PathVariable("type") String type){
        Map<String, Object> map = dailyService.getChartData(begin, end, type);
        return ResponseResult.success().data(map);
    }
}

