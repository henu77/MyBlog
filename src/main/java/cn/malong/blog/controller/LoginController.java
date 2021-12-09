package cn.malong.blog.controller;

import cn.malong.blog.pojo.UserInfo;
import cn.malong.blog.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * @author malong
 */
@RestController
@RequestMapping("/user")
public class LoginController {

    @Autowired
    private LoginService loginServiceImpl;

    @PostMapping("/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        @RequestParam("captcha") String captcha,
                        HttpSession session) {
        return loginServiceImpl.login(username, password, captcha, session);
    }

    @RequestMapping("/logout")
    public String logOut(HttpSession session) {
        return loginServiceImpl.logout(session);
    }

    @PostMapping("/register")
    public String register(@RequestBody UserInfo userInfo) {
        return loginServiceImpl.register(userInfo);
    }

    @PutMapping("/resetPwd")
    public String resetPwd(@RequestBody UserInfo userInfo) {
        return loginServiceImpl.resetPwd(userInfo);
    }


    @RequestMapping("/getDefaultIcon")
    public String getDefaultIcon() {
        return loginServiceImpl.getDefaultIcon();
    }
}
