package cn.malong.blog.controller;

import cn.malong.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author malong
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userServiceImpl;

    @RequestMapping("/data")
    public String getAllUserData() {
        return userServiceImpl.getAllUserData();
    }


    @RequestMapping("/dataLimit")
    public String getUserDataLimit(int page, int limit, String nickname, String username) {
//        System.out.println("nickname==" + nickname + ",username==" + username);
        return userServiceImpl.getUserDataByLimit(page, limit, nickname, username);
    }
}
