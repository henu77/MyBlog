package cn.malong.blog.dao;

import cn.malong.blog.pojo.SocialUser;
import cn.malong.blog.pojo.SocialUserAuth;
import cn.malong.blog.pojo.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author marlone
 * @Date 2021/12/3 18:28
 */
@Mapper
@Repository
public interface SocialUserAuthMapper {
    SocialUserAuth queryBySocialUserId(int id);

    int addSocialUserAuth(@Param("userId") int userId, @Param("SocialUserId") int SocialUserId);

}
