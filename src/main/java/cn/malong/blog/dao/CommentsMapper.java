package cn.malong.blog.dao;

import cn.malong.blog.pojo.Comment;
import cn.malong.blog.pojo.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author malong
 * @Date 2021-10-31 15:57:37
 */
@Mapper
@Repository
public interface CommentsMapper {

    /**
     * 分页得到评论
     *
     * @param startIndex
     * @param pageSize
     * @return
     */
    List<Comment> getCommentDataByLimit(@Param("startIndex") int startIndex, @Param("pageSize") int pageSize);

    int deleteComment(@Param("id") int id);

    int countCommentsByBlogId(int blogId);
}
