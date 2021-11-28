package cn.malong.blog.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author marlone
 * @Date 2021/11/28 15:06
 */
@Component
public class TrafficStatisticsInterceptor implements HandlerInterceptor {

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
            trafficStatistics = 0;
        }
        trafficStatistics++;
        servletContext.setAttribute("TrafficStatistics", trafficStatistics);
    }
}
