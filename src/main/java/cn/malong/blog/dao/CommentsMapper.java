package cn.malong.blog.dao;

import cn.malong.blog.pojo.Blog;
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

    /**
     * 动态sql 基于昵称和内容进行查找
     *
     * @param startIndex
     * @param pageSize
     * @param nickname
     * @param content
     * @return
     */
    List<Comment> getCommentDataByNicknameAndContent(@Param("startIndex") int startIndex, @Param("pageSize") int pageSize, @Param("nickname") String nickname, @Param("content") String content);

    int deleteComment(@Param("id") int id);

    /**
     * 批量删除
     */
    int deleteCommentsByBatch(int[] ids);

    int countComment();

    int countCommentsByBlogId(int blogId);

    int insertAComment(Comment comment);

    List<Comment> getAllCommentsByBlogId(int blogId);

    List<Comment> getAllChildComments(int id);
}
