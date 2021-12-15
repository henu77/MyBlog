package cn.malong.blog.service.impl;

import cn.malong.blog.dao.UserInfoMapper;
import cn.malong.blog.pojo.UserInfo;
import cn.malong.blog.service.FileService;
import cn.malong.blog.service.LoginService;
import cn.malong.blog.utils.MD5Util;
import cn.malong.blog.utils.ResponseUtil;
import cn.malong.blog.utils.StaticVariable;
import cn.malong.blog.utils.SysFileUtil;
import cn.malong.blog.utils.servlet.ServletUtil;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.util.*;

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
    public String login(String username, String password, String captcha, HttpSession session) {
        ResponseUtil<String> json = new ResponseUtil<String>();
        UserInfo userInfoFromSession = UserServiceImpl.getUserInfoFromSession();
        if (null == userInfoFromSession) {
            UserInfo userInfo = userInfoMapper.getUserInfoByUsername(username);
            if (null == userInfo) {
                json.setCode(0);
                json.setMsg("用户名不存在");
                return json.toString();
            }
            String captchaFromSession = (String) ServletUtil.getSession().getAttribute("login_VerifyCode");
            if (null == captchaFromSession) {
                json.setCode(0);
                json.setMsg("验证码已失效！");
                return json.toString();
            }
            if (!captcha.equals(captchaFromSession)) {
                json.setCode(0);
                json.setMsg("验证码错误!");
                return json.toString();
            }
            if (!MD5Util.passwordIsTrue(password, userInfo.getPassword())) {
                json.setCode(0);
                json.setMsg("密码错误");
                return json.toString();
            }
            session.setAttribute("userInfo", userInfo);
            json.setCode(1);
            json.setMsg("登录成功");
        } else {
            json.setCode(0);
            json.setMsg("您已经登录，请先注销！");
        }

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

    @Override
    public String register(UserInfo userInfo) {
        ResponseUtil<String> json = new ResponseUtil<>();
        String userInfoRole = userInfo.getRole();
        //权限验证
        if (userInfoRole.equals(StaticVariable.ROLE_USER)) {
            //两次密码验证
            if (userInfo.getPassword().equals(userInfo.getTestPassword())) {
                userInfo.setPassword(MD5Util.string2MD5(userInfo.getPassword()));
                int result = userInfoMapper.userAdd(userInfo);
                if (result > 0) {
                    json.setCode(1);
                    json.setMsg("注册成功！");
                    ServletUtil.getSession().removeAttribute("registerEmailVerifyCode");
                } else {
                    json.setCode(0);
                    json.setMsg("注册失败！");
                }
            } else {
                json.setCode(0);
                json.setMsg("两次输入的密码不一致！");
            }
        } else {
            json.setCode(0);
            json.setMsg("不能注册更高权限的用户！");
        }
        return json.toString();
    }

    @Override
    public String getDefaultIcon() {
        ResponseUtil<String> json = new ResponseUtil<>();
        String avatar = StaticVariable.getDefaultIconPath();
        List<String> data = new ArrayList<>();
        data.add(avatar);
        json.setMsg("获取默认头像成功");
        json.setCode(1);
        json.setData(data);
        return json.toString();
    }

    @Override
    public String resetPwd(UserInfo userInfo) {
        ResponseUtil<String> json = new ResponseUtil<>();
        String retrievePasswordEmailVerifyCode =
                (String) ServletUtil.getSession().getAttribute("retrievePasswordEmailVerifyCode");
        if (null == retrievePasswordEmailVerifyCode) {
            json.setCode(0);
            json.setMsg("您还未发送验证码！");
            return json.toString();
        }
        if (!retrievePasswordEmailVerifyCode.equals(userInfo.getVerifyCode())) {
            json.setCode(0);
            json.setMsg("验证码错误！");
            return json.toString();
        }
        UserInfo userInfoByUsername = userInfoMapper.getUserInfoByUsername(userInfo.getUsername());
        if (null == userInfoByUsername) {
            json.setCode(0);
            json.setMsg("用户名不存在");
            return json.toString();
        }
        if (!userInfo.getPassword().equals(userInfo.getTestPassword())) {
            json.setCode(0);
            json.setMsg("两次输入的密码不一致");
            return json.toString();
        }
        int result = userInfoMapper.updatePwd(userInfoByUsername.getId(), MD5Util.string2MD5(userInfo.getTestPassword()));
        if (result > 0) {
            json.setCode(1);
            json.setMsg("修改成功！");
        } else {
            json.setCode(0);
            json.setMsg("修改失败！更新错误");
        }
        return json.toString();
    }

    /**
     * 定时删除session中的信息
     *
     * @param attrName
     */
    private void removeAttributeFromSession(final String attrName, int seconds) {
        HttpSession session = ServletUtil.getSession();
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                session.removeAttribute(attrName);
                timer.cancel();
            }
        }, seconds * 1000);
    }

}
