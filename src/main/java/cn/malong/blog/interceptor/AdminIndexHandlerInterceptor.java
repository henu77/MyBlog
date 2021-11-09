package cn.malong.blog.interceptor;

import cn.malong.blog.pojo.UserInfo;
import cn.malong.blog.utils.StaticVariable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @author malong
 * @Date 2021-10-29 22:31:30
 */
@Component
public class AdminIndexHandlerInterceptor implements HandlerInterceptor {


    /**
     * 如果用户已登录且 权限为 root 或 admin 放行。
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        UserInfo userInfo = (UserInfo) request.getSession().getAttribute("userInfo");
        if (null == userInfo) {
            request.setAttribute("Interceptor_msg", "请先登录");
            request.getRequestDispatcher("/login.html").forward(request, response);
            return false;
        } else {
            String role = userInfo.getRole();
            if (StaticVariable.ROLE_ADMIN.equals(role) || StaticVariable.ROLE_ROOT.equals(role)) {
                return true;
            } else {
                request.setAttribute("Interceptor_msg", "权限不够请切换账号");
                request.getRequestDispatcher("/login.html").forward(request, response);
                return false;
            }
        }
    }
}
