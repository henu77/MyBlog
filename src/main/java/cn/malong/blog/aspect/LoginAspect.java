package cn.malong.blog.aspect;

import cn.malong.blog.dao.UserInfoMapper;
import cn.malong.blog.pojo.UserInfo;
import cn.malong.blog.utils.ResponseUtil;
import cn.malong.blog.utils.servlet.ServletUtil;
import com.alibaba.fastjson.JSONObject;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * @author marlone
 * @Date 2021/11/24 13:34
 */
@Aspect
@Component
public class LoginAspect {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Around("execution(* cn.malong.blog.controller.LoginController.login(..))")
    public String around(ProceedingJoinPoint pj) throws Throwable {
        Object[] args = pj.getArgs();
        String result = (String) pj.proceed(args);
        ResponseUtil<String> json = JSONObject.parseObject(result, ResponseUtil.class);
        if (json.getCode() == 1) {
            //登录成功
            String username = (String) args[0];
            UserInfo userInfoByUsername = userInfoMapper.getUserInfoByUsername_recent(username);
            String avatar = userInfoByUsername.getAvatar();
            if (!avatar.startsWith("D:")) {
                userInfoByUsername.setAvatar("/linux" + avatar);
            }
            ServletContext servletContext = ServletUtil.getRequest().getServletContext();
            List<UserInfo> recentViewUser = (List<UserInfo>) servletContext.getAttribute("recentViewUser");
            if (null == recentViewUser) {
                recentViewUser = new ArrayList<>();
            }
            recentViewUser.add(userInfoByUsername);
            if (recentViewUser.size() > 12) {
                recentViewUser.remove(0);
            }
            servletContext.setAttribute("recentViewUser", recentViewUser);
        }
        return result;
    }
}
