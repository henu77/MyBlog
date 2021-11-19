package cn.malong.blog.service;

import cn.malong.blog.pojo.Comment;

/**
*
* @author malong
* @Date 2021-10-31 15:56:12
*/
public interface CommentsService {
    /**
     * 获得评论信息
     * @return
     */
    String getCommentDataLimit(int page, int limit);

    String getCommentDataByNicknameAndContent(int page,int limit,String nickname,String content);

    String commentDelete(int id);

    String commentBatchDelete(int[] ids);

    String commentBlog(Comment comment);

    String getCommentByBlogId(int blogId);

    String replyTopComment(int parentCommentId, int repliedUserId, int blogId, String content);
}
