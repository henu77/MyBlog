package cn.malong.blog.service;

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
}
