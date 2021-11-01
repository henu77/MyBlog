package cn.malong.blog.controller;

import cn.malong.blog.pojo.UserInfo;
import cn.malong.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

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
        return userServiceImpl.getUserDataByLimit(page, limit, nickname, username);
    }

    @PutMapping(value = "/updateUserDate", consumes = "application/json")
    public String updateUserDate(@RequestBody UserInfo userInfo) {
        return userServiceImpl.updateUserDate(userInfo);
    }

    @PutMapping("/updateAvatar")
    public String updateAvatar(String avatar, HttpServletRequest request) {
        return userServiceImpl.updateAvatar(avatar, request);
    }

}
