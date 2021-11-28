package cn.malong.blog.service;

import java.util.Date;

/**
 * @author marlone
 * @Date 2021/11/28 15:34
 */
public interface TimedTaskService {

    //1 0 0 * * ?
    /**
     * 每天 00：01初始化今天的访问量
     */
    void createTodayTrafficStatistics();


    //0 0/5 * * * ?
    /**
     * 每隔 5 分钟 访问量更新到数据库
     */
    void updateTrafficStatistics();
}
