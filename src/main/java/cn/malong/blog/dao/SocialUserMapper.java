package cn.malong.blog.dao;

import cn.malong.blog.pojo.SocialUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author marlone
 * @Date 2021/12/3 18:28
 */
@Mapper
@Repository
public interface SocialUserMapper {
    SocialUser querySocialUserByUUidAndSource(@Param("uuid") String uuid,@Param("source") String source);
    SocialUser querySocialUserById(int id);
    int addSocialUser(@Param("socialUser") SocialUser socialUser);
    int updateSocialUser(@Param("socialUser") SocialUser socialUser);
}
