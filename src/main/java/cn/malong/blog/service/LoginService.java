package cn.malong.blog.service;

import cn.malong.blog.pojo.UserInfo;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;

/**
 * @author malong
 */
public interface LoginService {
    /**
     * 使用账号密码进行登录
     *
     * @param username
     * @param password
     * @param session
     * @return
     */
    String login(String username, String password, String captcha, HttpSession session);

    /**
     * 注销session 退出登录
     *
     * @param session
     * @return
     */
    String logout(HttpSession session);

    String register(UserInfo userInfo);

    String getDefaultIcon();
}
