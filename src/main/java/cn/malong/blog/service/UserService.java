package cn.malong.blog.service;

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
}
