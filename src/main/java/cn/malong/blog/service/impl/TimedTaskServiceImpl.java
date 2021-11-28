package cn.malong.blog.service.impl;

import cn.malong.blog.dao.ProvinceMapper;
import cn.malong.blog.dao.TrafficStaticsMapper;
import cn.malong.blog.pojo.TrafficStatics;
import cn.malong.blog.service.TimedTaskService;
import cn.malong.blog.utils.servlet.ServletUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.servlet.ServletContext;
import java.util.Date;

/**
 * @author marlone
 * @Date 2021/11/28 15:42
 */
@Service
@Slf4j
public class TimedTaskServiceImpl implements TimedTaskService {
    private TrafficStatics trafficStatics = new TrafficStatics(0);
    @Autowired
    private TrafficStaticsMapper trafficStaticsMapper;
    @Autowired
    private ServletContext servletContext;
    @Autowired
    private ProvinceMapper provinceMapper;

    @Override
    @Scheduled(cron = "1 0 0 * * ?")
    public void createTodayTrafficStatistics() {
        if (trafficStatics.getViews() != 0) {
            trafficStatics.setViews(0);
        }
        servletContext.setAttribute("TrafficStatistics", 0);
        int result = trafficStaticsMapper.initTodayViews(trafficStatics);
        log.info("访问量统计初始化结果==>" + (result >= 1 ? true : false));

    }

    @Override
    @Scheduled(cron = "0 0/5 * * * ?")
    public void updateTrafficStatistics() {
        int views = (int) servletContext.getAttribute("TrafficStatistics");
        trafficStatics.setViews(views);
        int result = trafficStaticsMapper.updateTodayViews(trafficStatics);
        log.info("访问量统计更新结果==>" + (result >= 1 ? true : false));
    }
}
