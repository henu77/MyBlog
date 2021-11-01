package cn.malong.blog.service;

import cn.malong.blog.pojo.UserInfo;

import javax.servlet.http.HttpServletRequest;

/**
 * @author malong
 */
public interface UserService {

    /**
     * 获得所有用户的信息
     * @return
     */
    String getAllUserData();

    /**
     * 获得部分用户信息，用于分页
     * @param page
     * @param limit
     * @return
     */
    String getUserDataByLimit(int page, int limit);

    /**
     * 获得部分用户信息，用于分页
     * @param page
     * @param limit
     * @param nickname
     * @param username
     * @return
     */
    String getUserDataByLimit(int page, int limit,String nickname,String username);

    /**
     * 更新用户信息
     * @param userInfo
     * @return
     */
    String updateUserDate(UserInfo userInfo);

    /**
     * 更新用户头像信息
     * @param avatar
     * @param request
     * @return
     */
    String updateAvatar(String avatar, HttpServletRequest request);
}
