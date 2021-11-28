package cn.malong.blog.dao;

import cn.malong.blog.pojo.TrafficStatics;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author marlone
 * @Date 2021/11/28 15:47
 */
@Mapper
@Repository
public interface TrafficStaticsMapper {

    int initTodayViews(@Param("trafficStatics") TrafficStatics trafficStatics);

    int updateTodayViews(@Param("trafficStatics") TrafficStatics trafficStatics);

    List<TrafficStatics> getNearlyAWeekData();
}
