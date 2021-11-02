package cn.malong.blog.service;

import cn.malong.blog.dao.UserInfoMapper;
import cn.malong.blog.pojo.UserInfo;
import cn.malong.blog.utils.MD5Util;
import cn.malong.blog.utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * @author malong
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    /**
     * 1. 通过用户名获取 userInfo
     * 2. 判断 userInfo是否为空
     * 为空则转发回登陆页面，并加入用户名不存在
     * 3. 判断是否密码一致
     * 不一致则转发回登录页面，并加入密码错误的提示信息
     * 4. session中存入userInfo 重定向到文章页
     *
     * @param username
     * @param password
     * @param session
     * @return
     */
    @Override
    public String login(String username, String password, HttpSession session) {
        ResponseUtil<String> json = new ResponseUtil<String>();
        UserInfo userInfo = userInfoMapper.getUserInfoByUsername(username);
        if (null == userInfo) {
            json.setCode(0);
            json.setMsg("用户名不存在");
//            System.out.println("用户名不存在");
            return json.toString();
        }
        if (!MD5Util.passwordIsTrue(password, userInfo.getPassword())) {
            json.setCode(0);
            json.setMsg("密码错误");
//            System.out.println("密码错误");
            return json.toString();
        }
        session.setAttribute("userInfo", userInfo);
        json.setCode(1);
        json.setMsg("登录成功");
//        System.out.println("登录成功");
        return json.toString();
    }

    @Override
    public String logout(HttpSession session) {
        session.invalidate();
        ResponseUtil<String> json = new ResponseUtil<String>();
        json.setMsg("注销成功");
        json.setCode(1);
        List<String> list = new ArrayList<>();
        list.add("/index.html");
        json.setData(list);
        return json.toString();
    }


}
