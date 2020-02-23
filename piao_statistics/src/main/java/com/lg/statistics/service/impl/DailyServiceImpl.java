package com.lg.statistics.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lg.statistics.entity.Daily;
import com.lg.statistics.feign.MemberClient;
import com.lg.statistics.mapper.DailyMapper;
import com.lg.statistics.service.DailyService;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author lg
 * @since 2020-02-11
 */
@Service
public class DailyServiceImpl extends ServiceImpl<DailyMapper, Daily> implements DailyService {

    @Autowired
    private MemberClient memberClient;

    @Override
    public void createStatisticsByDay(String day) {
        //删除已存在的统计对象
        QueryWrapper<Daily> dayQueryWrapper = new QueryWrapper<>();
        dayQueryWrapper.eq("date_calculated", day);
        baseMapper.delete(dayQueryWrapper);


        //获取统计信息
        Integer registerNum = (Integer) memberClient.registerCount(day).getData().get("item");
        Integer loginNum = RandomUtils.nextInt(100, 200);//TODO
        Integer videoViewNum = RandomUtils.nextInt(100, 200);//TODO
        Integer courseNum = RandomUtils.nextInt(100, 200);//TODO

        //创建统计对象
        Daily daily = new Daily();
        daily.setRegisterNum(registerNum);
        daily.setLoginNum(loginNum);
        daily.setVideoViewNum(videoViewNum);
        daily.setCourseNum(courseNum);
        daily.setDateCalculated(day);

        baseMapper.insert(daily);
    }

    @Override
    public Map<String, Object> getChartData(String begin, String end, String type) {
        QueryWrapper<Daily> dayQueryWrapper = new QueryWrapper<>();
        dayQueryWrapper.orderByAsc("date_calculated");
        dayQueryWrapper.select(type, "date_calculated");
        dayQueryWrapper.between("date_calculated", begin, end);

        List<Daily> dayList = baseMapper.selectList(dayQueryWrapper);

        Map<String, Object> map = new HashMap<>();
        List<Integer> dataList = new ArrayList<>();
        List<String> dateList = new ArrayList<>();
        map.put("dataList", dataList);
        map.put("dateList", dateList);

        for (Daily daily : dayList) {
            dateList.add(daily.getDateCalculated());
            switch (type) {
                case "register_num":
                    dataList.add(daily.getRegisterNum());
                    break;
                case "login_num":
                    dataList.add(daily.getLoginNum());
                    break;
                case "video_view_num":
                    dataList.add(daily.getVideoViewNum());
                    break;
                case "course_num":
                    dataList.add(daily.getCourseNum());
                    break;
                default:
                    break;
            }
        }
        return map;
    }
}
