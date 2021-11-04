package cn.malong.blog.service.impl;

import cn.malong.blog.dao.UserInfoMapper;
import cn.malong.blog.pojo.UserInfo;
<<<<<<< HEAD:src/main/java/cn/malong/blog/service/UserServiceImpl.java
import cn.malong.blog.utils.MD5Util;
=======
import cn.malong.blog.service.UserService;
>>>>>>> origin/master:src/main/java/cn/malong/blog/service/impl/UserServiceImpl.java
import cn.malong.blog.utils.ResponseUtil;
import cn.malong.blog.utils.StaticString;
import cn.malong.blog.utils.servlet.ServletUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @author malong
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    /**
     * 所有用户可用
     */
    @Override
    public String getAllUserData() {
        List<UserInfo> allUserData = userInfoMapper.getAllUserData();
        return userDataToJson(allUserData);
    }

    @Override
    public String updateAvatar(String avatar, HttpServletRequest request) {
        ResponseUtil<String> json = new ResponseUtil<>();
        UserInfo userInfo = (UserInfo) request.getSession().getAttribute("userInfo");
        userInfo.setAvatar(avatar);
        int result = userInfoMapper.updateAvatar(userInfo);
        if (result > 0) {
            json.setCode(1);
            json.setMsg("更新成功");
        } else {
            json.setCode(0);
            json.setMsg("更新失败");
        }
        return json.toString();
    }

    /**
     * 以下函数仅对普通用户进行操作，即权限为user的用户
     */

    /**
     * 执行流程
     * 1.如果 用户输入的 nickname和 username都为 空 则直接调用 只含 page，limit的查询函数
     * 2.如果有一个不为空 则 根据情况调用相应的查询函数
     *
     * @param page
     * @param limit
     * @param nickname
     * @param username
     * @return
     */
    @Override
    public String getUserDataByLimit(int page, int limit, String nickname, String username) {
        int startIndex = (page - 1) * limit;
        List<UserInfo> userInfoList = new ArrayList<>();
        //用户输入的 nickname和 username都为 空 则直接调用 只含 page，limit的查询函数
        if (isEmpty(nickname) && isEmpty(username)) {
            userInfoList =
                    userInfoMapper.getUserDataByLimit(startIndex, limit);
        } else if (isEmpty(nickname)) {
            userInfoList =
                    userInfoMapper.getUserDataByLimit_username(startIndex, limit, username);
        } else if (isEmpty(username)) {
            userInfoList =
                    userInfoMapper.getUserDataByLimit_nickname(startIndex, limit, nickname);
        } else {
            userInfoList =
                    userInfoMapper.getUserDataByLimit_nick_username(startIndex, limit, nickname, username);
        }
        return userDataToJson(userInfoList);
    }

    /**
     * user-setting中使用
     *
     * @param newUserInfo
     * @return
     */
    @Override
    public String updateUserDate(UserInfo newUserInfo) {
        UserInfo userInfoById = userInfoMapper.getUserInfoById(newUserInfo.getId());
        userInfoById.setEmail(newUserInfo.getEmail());
        userInfoById.setNickname(newUserInfo.getNickname());
        return updateResult(userInfoById);
    }


    @Override
    public String updateUserDate_userEdit(UserInfo userInfo) {
        UserInfo userInfoById = userInfoMapper.getUserInfoById(userInfo.getId());
        UserInfo nowUserInfo = (UserInfo) ServletUtil.getSession().getAttribute("userInfo");
        if (nowUserInfo.getRole().equals(StaticString.ROLE_ADMIN)
                && userInfo.getRole().equals(StaticString.ROLE_ROOT) || userInfoById.getRole().equals(StaticString.ROLE_ROOT)) {
            //如果当前用户 权限为 admin 但是想要修改用户权限为 root则禁止
            ResponseUtil<String> json = new ResponseUtil<>();
            json.setCode(0);
            json.setMsg("修改失败，您没有权限修改!");
            return json.toString();
        }
        userInfoById.setEmail(userInfo.getEmail());
        userInfoById.setRole(userInfo.getRole());
        userInfoById.setUsername(userInfo.getUsername());
        userInfoById.setNickname(userInfo.getNickname());
        userInfoById.setPassword(MD5Util.string2MD5(userInfo.getPassword()));
        return updateResult(userInfoById);
    }

    /**
     * 以下函数对管理页用户进行操作，即权限为admin或root的用户
     */
    @Override
    public String getUserDataByLimitAdmin(int page, int limit, String nickname, String username) {
        int startIndex = (page - 1) * limit;
        List<UserInfo> userInfoList = new ArrayList<>();
        //用户输入的 nickname和 username都为 空 则直接调用 只含 page，limit的查询函数
        if (isEmpty(nickname) && isEmpty(username)) {
            userInfoList =
                    userInfoMapper.getUserDataByLimitAdmin(startIndex, limit);
        } else if (isEmpty(nickname)) {
            userInfoList =
                    userInfoMapper.getUserDataByLimit_usernameAdmin(startIndex, limit, username);
        } else if (isEmpty(username)) {
            userInfoList =
                    userInfoMapper.getUserDataByLimit_nicknameAdmin(startIndex, limit, nickname);
        } else {
            userInfoList =
                    userInfoMapper.getUserDataByLimit_nick_usernameAdmin(startIndex, limit, nickname, username);
        }
        return userDataToJson(userInfoList);
    }


    /**
     * 以下为工具函数
     */
    private String updateResult(UserInfo userInfoById) {
        int result = userInfoMapper.updateUserInfo(userInfoById);
        ResponseUtil<String> json = new ResponseUtil<>();
        if (result > 0) {
            json.setCode(1);
            json.setMsg("修改成功");
        } else {
            json.setCode(0);
            json.setMsg("修改失败，请检查sql语句");
        }
        return json.toString();
    }

    private boolean isEmpty(String s) {
        if (null == s) {
            return true;
        }
        if ("".equals(s)) {
            return true;
        }
        if ("".equals(s.trim())) {
            return true;
        }
        return false;
    }

    private String userDataToJson(List<UserInfo> userData) {
        ResponseUtil<UserInfo> json = new ResponseUtil<>();
        if (null == userData) {
            json.setCode(1);
            json.setMsg("获取用户信息失败");
        } else if (userData.isEmpty()) {
            json.setCode(1);
            json.setMsg("用户信息为空");
        } else {
            json.setCode(0);
            json.setCount(userData.size());
            json.setMsg("获取用户信息成功");
            json.setData(userData);
        }
        return json.toString();
    }
}
