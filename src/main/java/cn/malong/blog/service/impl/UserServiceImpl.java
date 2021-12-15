package cn.malong.blog.service.impl;

import cn.malong.blog.dao.UserInfoMapper;
import cn.malong.blog.pojo.UserInfo;
import cn.malong.blog.utils.*;
import cn.malong.blog.service.UserService;
import cn.malong.blog.utils.servlet.ServletUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
        return userDataToJson(allUserData, StaticVariable.ROLE_USER);
    }

    @Override
    public String updateAvatar(String avatar, HttpServletRequest request) {
        ResponseUtil<String> json = new ResponseUtil<>();
        UserInfo userInfo = getUserInfoFromSession();
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
        /**
         * 参数欲处理
         */
        int startIndex = (page - 1) * limit;
        if (null != nickname) {
            nickname = nickname.replace(StaticVariable.SPACE, "");
            if (nickname.equals("")) {
                nickname = null;
            }
        }
        if (null != username) {
            username = username.replace(StaticVariable.SPACE, "");

            if (username.equals("")) {
                username = null;
            }
        }
        List<UserInfo> userInfoList = new ArrayList<>();
        userInfoList =
                userInfoMapper.getUserDataByLimit(startIndex, limit, nickname, username, "user");
        return userDataToJson(userInfoList, StaticVariable.ROLE_USER);
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
        ResponseUtil<String> json = new ResponseUtil<>();
        if (!isHavingAuthority()) {
            //用户权限为 user 时
            json.setCode(0);
            json.setMsg("您的权限太低！");
            return json.toString();
        }
        UserInfo userInfoByUsername = userInfoMapper.getUserInfoByUsername(userInfo.getUsername());
        if (null != userInfoByUsername) {
            json.setCode(0);
            json.setMsg("此用户名已存在！");
            return json.toString();
        }
        UserInfo userInfoById = userInfoMapper.getUserInfoById(userInfo.getId());
        UserInfo nowUserInfo = getUserInfoFromSession();
        if (nowUserInfo.getRole().equals(StaticVariable.ROLE_ADMIN)
                && userInfo.getRole().equals(StaticVariable.ROLE_ROOT) || userInfoById.getRole().equals(StaticVariable.ROLE_ROOT)) {
            //如果当前用户 权限为 admin 但是想要修改用户权限为 root则禁止
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
        /**
         * 参数预处理
         */
        int startIndex = (page - 1) * limit;
        if (null != nickname) {
            nickname = nickname.replace(StaticVariable.SPACE, "");
            if (nickname.equals("")) {
                nickname = null;
            }
        }
        if (null != username) {
            username = username.replace(StaticVariable.SPACE, "");

            if (username.equals("")) {
                username = null;
            }
        }
        List<UserInfo> userInfoList = new ArrayList<>();
        userInfoList =
                userInfoMapper.getUserDataByLimitAdmin(startIndex, limit, nickname, username, "admin");
        return userDataToJson(userInfoList, StaticVariable.ROLE_ADMIN);
    }

    @Override
    public String removeUser(int id) {
        if (!isHavingAuthority()) {
            //用户权限为 user 时
            ResponseUtil<String> json = new ResponseUtil<>();
            json.setCode(0);
            json.setMsg("您的权限太低！");
            return json.toString();
        }
        UserInfo nowUserInfo = getUserInfoFromSession();
        if (id == nowUserInfo.getId()) {
            ResponseUtil<String> json = new ResponseUtil<>();
            json.setCode(0);
            json.setMsg("您不能删除您自己！");
            return json.toString();
        }
        int result = userInfoMapper.removeUserById(id);
        return getDeleteResult(result);
    }

    @Override
    public String batchRemove(int[] ids) {
        if (!isHavingAuthority()) {
            //用户权限为 user 时
            ResponseUtil<String> json = new ResponseUtil<>();
            json.setCode(0);
            json.setMsg("您的权限太低！");
            return json.toString();
        }
        UserInfo nowUserInfo = getUserInfoFromSession();
        for (int i = 0; i < ids.length; i++) {
            if (ids[i] == nowUserInfo.getId()) {
                ResponseUtil<String> json = new ResponseUtil<>();
                json.setCode(0);
                json.setMsg("您不能删除您自己！");
                return json.toString();
            }
        }
        int result = userInfoMapper.batchRemoveByIds(ids);
        return getDeleteResult(result);
    }

    @Override
    public String userAdd(UserInfo userInfo) {
        if (!isHavingAuthority()) {
            //用户权限为 user 时
            ResponseUtil<String> json = new ResponseUtil<>();
            json.setCode(0);
            json.setMsg("您的权限太低！");
            return json.toString();
        }
        ResponseUtil<String> json = new ResponseUtil<>();
        UserInfo nowUserInfo = getUserInfoFromSession();
        if (nowUserInfo.getRole().equals(StaticVariable.ROLE_ROOT)
                || (nowUserInfo.getRole().equals(StaticVariable.ROLE_ADMIN) && !userInfo.getRole().equals(StaticVariable.ROLE_ROOT))) {
            userInfo.setPassword(MD5Util.string2MD5(userInfo.getPassword()));
            userInfo.setAvatar(SysFileUtil.getUploadPath() + "default.png");
            int result = userInfoMapper.userAdd(userInfo);
            if (result > 0) {
                json.setCode(1);
                json.setMsg("新增成功！");
            } else {
                json.setCode(0);
                json.setMsg("新增失败！");
            }
        } else {
            json.setCode(0);
            json.setMsg("您没有权限设置新增用户的权限！");
        }
        return json.toString();
    }

    /**
     * 判断旧密码是否正确
     *
     * @param updatePwdUtil
     * @return
     */
    @Override
    public String editOwnPassword(UpdatePwdUtil updatePwdUtil) {
        ResponseUtil<String> json = new ResponseUtil<>();
        UserInfo userInfoFromSession = getUserInfoFromSession();
        if (updatePwdUtil.getOldPassword().equals(userInfoFromSession.getPassword())) {
            if (updatePwdUtil.isSame()) {
                userInfoFromSession.setPassword(updatePwdUtil.getNewPassword());
                int result = userInfoMapper.updateUserInfo(userInfoFromSession);
                if (result > 0) {
                    json.setCode(1);
                    json.setMsg("更新成功！");
                } else {
                    json.setCode(0);
                    json.setMsg("密码更新失败！");
                }
            } else {
                json.setCode(0);
                json.setMsg("两次输入的密码不一致！");
            }
        } else {
            json.setCode(0);
            json.setMsg("旧密码错误！");
        }
        return json.toString();
    }

    @Override
    public String queryByUsername(String username) {
        ResponseUtil<Boolean> json = new ResponseUtil<>();
        ArrayList<Boolean> data = new ArrayList<>();
        Boolean isHaving = userInfoMapper.queryByUserName(username);
        json.setCode(1);
        json.setMsg("查询成功");
        if (null != isHaving) {
            data.add(true);
        } else {
            data.add(false);
        }
        json.setData(data);
        return json.toString();
    }


    /**
     * 以下为工具函数
     */
    private String getDeleteResult(int result) {
        ResponseUtil<String> json = new ResponseUtil<>();
        if (result > 0) {
            json.setCode(1);
            json.setMsg("删除成功");
        } else {
            json.setCode(0);
            json.setMsg("删除失败");
        }
        return json.toString();
    }

    private String updateResult(UserInfo userInfoById) {
        int result = userInfoMapper.updateUserInfo(userInfoById);
        ResponseUtil<String> json = new ResponseUtil<>();
        if (result > 0) {
            json.setCode(1);
            json.setMsg("修改成功");
            if (userInfoById.getUsername().equals(getUserInfoFromSession().getUsername())) {
                refreshUserInfo(userInfoById);
            }
        } else {
            json.setCode(0);
            json.setMsg("修改失败，请检查sql语句");
        }
        return json.toString();
    }

    private void refreshUserInfo(UserInfo userInfo) {
        HttpSession session = ServletUtil.getSession();
        session.removeAttribute("userInfo");
        session.setAttribute("userInfo", userInfo);
    }

    public static UserInfo getUserInfoFromSession() {
        UserInfo userInfo = (UserInfo) ServletUtil.getSession().getAttribute("userInfo");
        return userInfo;
    }

    public static boolean isHavingAuthority() {
        UserInfo userInfoFromSession = getUserInfoFromSession();
        if (userInfoFromSession.getRole().equals(StaticVariable.ROLE_USER)) {
            return false;
        } else if (userInfoFromSession.getRole().equals(StaticVariable.ROLE_ADMIN)
                || userInfoFromSession.getRole().equals(StaticVariable.ROLE_ROOT)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isHavingRootAuthority() {
        UserInfo userInfoFromSession = getUserInfoFromSession();
        if (userInfoFromSession.getRole().equals(StaticVariable.ROLE_USER)) {
            return false;
        } else if (userInfoFromSession.getRole().equals(StaticVariable.ROLE_ROOT)) {
            return true;
        } else {
            return false;
        }
    }

    private String userDataToJson(List<UserInfo> userData, String role) {
        ResponseUtil<UserInfo> json = new ResponseUtil<>();
        if (null == userData) {
            json.setCode(1);
            json.setMsg("获取用户信息失败");
        } else if (userData.isEmpty()) {
            json.setCode(1);
            json.setMsg("用户信息为空");
        } else {
            json.setCode(0);
            if (role.equals(StaticVariable.ROLE_USER)) {
                json.setCount(userInfoMapper.countAllUser());

            } else {
                json.setCount(userInfoMapper.countAllAdmin());
            }
            json.setMsg("获取用户信息成功");
            json.setData(userData);
        }
        return json.toString();
    }
}
