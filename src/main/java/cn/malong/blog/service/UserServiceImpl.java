package cn.malong.blog.service;

import cn.malong.blog.dao.UserInfoMapper;
import cn.malong.blog.pojo.UserInfo;
import cn.malong.blog.utils.ResponseUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    @Override
    public String getAllUserData() {
        List<UserInfo> allUserData = userInfoMapper.getAllUserData();
        return userDataToJson(allUserData);
    }

    @Override
    public String getUserDataByLimit(int page, int limit) {
        int startIndex = (page - 1) * limit;
        List<UserInfo> userDataByLimit = userInfoMapper.getUserDataByLimit(startIndex, limit);
        return userDataToJson(userDataByLimit);
    }

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
            return getUserDataByLimit(page, limit);
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

    @Override
    public String updateUserDate(UserInfo newUserInfo) {
        UserInfo userInfoById = userInfoMapper.getUserInfoById(newUserInfo.getId());
        System.out.println(newUserInfo);
        userInfoById.setEmail(newUserInfo.getEmail());
//        userInfoById.setRole(newUserInfo.getRole());
//        userInfoById.setUsername(newUserInfo.getUsername());
        userInfoById.setNickname(newUserInfo.getNickname());
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
