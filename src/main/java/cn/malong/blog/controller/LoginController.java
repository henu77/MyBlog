package cn.malong.blog.controller;

import cn.malong.blog.service.LoginServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * @author malong
 */
@RestController
public class LoginController {

    @Autowired
    private LoginServiceImpl loginService;

    @PostMapping("/user/login")
    @ResponseBody
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        HttpSession session) {
        return loginService.login(username, password, session);
    }

    @RequestMapping("/user/logout")
    public String logOut(HttpSession session) {
        return loginService.logout(session);
    }
}
