package cn.malong.blog.service.impl;

import cn.malong.blog.dao.ProvinceMapper;
import cn.malong.blog.dao.TrafficStaticsMapper;
import cn.malong.blog.pojo.TrafficStatics;
import cn.malong.blog.service.TimedTaskService;
import cn.malong.blog.utils.DateUtils;
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
    @Autowired
    private TrafficStaticsMapper trafficStaticsMapper;
    @Autowired
    private ServletContext servletContext;

    @Override
    @Scheduled(cron = "5 0 0 * * ?")
    public void createTodayTrafficStatistics() {
        servletContext.removeAttribute("TrafficStatistics");
        TrafficStatics trafficStatics = new TrafficStatics();
        trafficStatics.setViews(0);
        servletContext.setAttribute("TrafficStatistics", trafficStatics.getViews());
        int result = trafficStaticsMapper.initTodayViews(trafficStatics);
        log.info("访问量统计初始化结果==>" + (result >= 1 ? true : false));
    }

    @Override
    @Scheduled(cron = "0 0/1 * * * ?")
    public void updateTrafficStatistics() {
        if (servletContext.getAttribute("TrafficStatistics") == null) {
            TrafficStatics todayViews = trafficStaticsMapper.getTodayViews(DateUtils.getToDayYY_MM_DD());
            if (null != todayViews) {
                log.info("servletContext中没有该信息  从数据库中读取的是" + todayViews.getViews());
                servletContext.setAttribute("TrafficStatistics", todayViews.getViews());
            } else {
                log.info("servletContext中没有该信息  且从数据库中读取的也是null");
                servletContext.setAttribute("TrafficStatistics", 0);
            }
        }

        int views = (int) servletContext.getAttribute("TrafficStatistics");
        TrafficStatics trafficStatics = new TrafficStatics();
        trafficStatics.setViews(views);
        log.info("当前时间===>" + trafficStatics.getDate());
        log.info("内容===>" + trafficStatics.toString());
        int result = trafficStaticsMapper.updateTodayViews(trafficStatics);
        log.info("访问量统计更新结果==>" + (result >= 1 ? true : false));
        if (result < 1) {
            TrafficStatics todayViews = trafficStaticsMapper.getTodayViews(DateUtils.getToDayYY_MM_DD());
            if (null == todayViews) {
                trafficStatics.setViews(0);
                trafficStatics.setDate(new Date());
                int result2 = trafficStaticsMapper.initTodayViews(trafficStatics);
                log.info("失败当天0点初始化时失败,访问量统计初始化结果===>" + (result2 >= 1 ? true : false));
            }
        }
    }
}
