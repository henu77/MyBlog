package cn.malong.blog.service;

import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;

/**
 * @author malong
 */
public interface LoginService {
    /**
     * 使用账号密码进行登录
     * @param username
     * @param password
     * @param session
     * @return
     */
    String login(String username, String password, HttpSession session);

    /**
     * 注销session 退出登录
     * @param session
     * @return
     */
    String logout(HttpSession session);
}
