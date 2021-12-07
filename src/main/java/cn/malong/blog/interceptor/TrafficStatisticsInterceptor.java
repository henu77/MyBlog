package cn.malong.blog.interceptor;

import cn.malong.blog.dao.TrafficStaticsMapper;
import cn.malong.blog.pojo.TrafficStatics;
import cn.malong.blog.utils.DateUtils;
import com.sun.org.apache.bcel.internal.generic.DUP;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * @author marlone
 * @Date 2021/11/28 15:06
 */
@Slf4j
@Component
public class TrafficStatisticsInterceptor implements HandlerInterceptor {
    @Autowired
    private TrafficStaticsMapper trafficStaticsMapper;
    /**
     * 在请求处理方法执行之后执行
     *
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response,
                           Object handler,
                           ModelAndView modelAndView) throws Exception {
        ServletContext servletContext = request.getServletContext();
        Integer trafficStatistics = (Integer) servletContext.getAttribute("TrafficStatistics");
        if (null == trafficStatistics) {
            TrafficStatics todayViews = trafficStaticsMapper.getTodayViews(DateUtils.getToDayYY_MM_DD());
            if (null!=todayViews){
                trafficStatistics=todayViews.getViews();
            }else {
                trafficStatistics = 0;
            }
        }
        trafficStatistics++;
        log.info("当前访问量===>" + trafficStatistics);
        servletContext.setAttribute("TrafficStatistics", trafficStatistics);
    }
}
