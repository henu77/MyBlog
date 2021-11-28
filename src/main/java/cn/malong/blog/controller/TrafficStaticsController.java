package cn.malong.blog.controller;

import cn.malong.blog.dao.ProvinceMapper;
import cn.malong.blog.service.TrafficStaticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author marlone
 * @Date 2021/11/28 16:51
 */
@RestController
@RequestMapping("/admin/trafficStatics")
public class TrafficStaticsController {
    @Autowired
    private TrafficStaticsService trafficStaticsServiceImpl;

    @RequestMapping("/getNearlyAWeekData")
    public String getNearlyAWeekData() {
        return trafficStaticsServiceImpl.getNearlyAWeekData();
    }

    @RequestMapping("/getUserFromData")
    public String getUserFromData(){
        return trafficStaticsServiceImpl.getUserFromData();
    }
}
