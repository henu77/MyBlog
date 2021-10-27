package cn.malong.blog.dao;

import cn.malong.blog.pojo.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author malong
 */
@Mapper
@Repository
public interface UserInfoMapper {

    /**
     * 通过用户名获得用户信息
     * @param username
     * @return
     */
    UserInfo getUserInfoByUsername(String username);
}
