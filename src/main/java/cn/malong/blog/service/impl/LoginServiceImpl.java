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
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

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
    @Autowired
    private FileService fileServiceImpl;

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
        String result = "";
        Random random = new Random();
        try {
            File file = ResourceUtils.getFile(SysFileUtil.getUploadPath() + "/defaultIcon" + "/defaultIcon" + (random.nextInt(5) + 1) + ".png");
            FileInputStream inputStream = new FileInputStream(file);
            MultipartFile multipartFile = new MockMultipartFile(file.getName(), file.getName(),
                    ContentType.APPLICATION_OCTET_STREAM.toString(), inputStream);
            result = fileServiceImpl.upload(multipartFile);
        } catch (Exception e) {
            e.printStackTrace();
            json.setCode(0);
            json.setMsg("获取默认头像失败！");
            result = json.toString();
        }
        return result;
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
