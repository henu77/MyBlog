package cn.malong.blog.controller;

import cn.malong.blog.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * @author malong
 */
@RestController
public class LoginController {

    @Autowired
    private LoginService loginServiceImpl;

    @PostMapping("/user/login")
    @ResponseBody
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        HttpSession session) {
        return loginServiceImpl.login(username, password, session);
    }

    @RequestMapping("/user/logout")
    public String logOut(HttpSession session) {
        return loginServiceImpl.logout(session);
    }
}
