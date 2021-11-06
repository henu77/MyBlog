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

    /**
     * 所有用户可用
     */
    @PutMapping("/updateAvatar")
    public String updateAvatar(String avatar, HttpServletRequest request) {
        return userServiceImpl.updateAvatar(avatar, request);
    }

    @RequestMapping("/data")
    public String getAllUserData() {
        return userServiceImpl.getAllUserData();
    }


    /**
     * 以下函数仅对普通用户进行操作，即权限为user的用户
     */
    @RequestMapping("/dataLimit")
    public String getUserDataLimit(int page, int limit, String nickname, String username) {
        return userServiceImpl.getUserDataByLimit(page, limit, nickname, username);
    }

    /**
     * user-setting页面中使用
     *
     * @param userInfo
     * @return
     */
    @PutMapping("/updateUserDate")
    public String updateUserDate_userSetting(@RequestBody UserInfo userInfo) {
        return userServiceImpl.updateUserDate(userInfo);
    }

    @PutMapping("/update")
    public String update_userEdit(@RequestBody UserInfo userInfo) {
        return userServiceImpl.updateUserDate_userEdit(userInfo);
    }

    @DeleteMapping("/remove")
    public String removeUser(int id) {
        return userServiceImpl.removeUser(id);
    }

    @DeleteMapping("/batchRemove")
    public String batchRemove(int[] ids) {
        return userServiceImpl.batchRemove(ids);
    }

    @PostMapping("/add")
    public String userAdd(@RequestBody UserInfo userInfo) {
        return userServiceImpl.
                userAdd(userInfo);
    }

    /**
     * 以下函数对管理页用户进行操作，即权限为admin或root的用户
     */
    @RequestMapping("/dataLimitAdmin")
    public String getUserDataLimitAdmin(int page, int limit, String nickname, String username) {
        return userServiceImpl.getUserDataByLimitAdmin(page, limit, nickname, username);
    }
}
