package cn.malong.blog.dao;

import cn.malong.blog.pojo.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author malong
 */
@Mapper
@Repository
public interface UserInfoMapper {

    /**
     * 通过用户名获得用户信息
     *
     * @param username
     * @return
     */
    UserInfo getUserInfoByUsername(String username);


    /**
     * 获得所有的用户信息
     *
     * @return
     */
    List<UserInfo> getAllUserData();

    /**
     * 获取部分用户信息，用于分页
     *
     * @param startIndex
     * @param pageSize
     * @return
     */
    List<UserInfo> getUserDataByLimit(@Param("startIndex") int startIndex, @Param("pageSize")
            int pageSize, String nickname, String username, @Param("role") String role);

    /**
     * 通过Id获得userInfo
     *
     * @param id
     * @return
     */
    UserInfo getUserInfoById(int id);

    /**
     * 更新用户信息
     *
     * @param userInfoById
     * @return
     */
    int updateUserInfo(UserInfo userInfoById);

    /**
     * 更新用户头像信息
     *
     * @param userInfo
     * @return
     */
    int updateAvatar(UserInfo userInfo);

    List<UserInfo> getUserDataByLimitAdmin(int startIndex, int pageSize, String nickname, String username, String role);

    int removeUserById(int id);

    int batchRemoveByIds(int[] ids);

    int userAdd(UserInfo userInfo);

    int countAllUser();

    int countAllAdmin();
}
