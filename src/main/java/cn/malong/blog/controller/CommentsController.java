package cn.malong.blog.controller;

import cn.malong.blog.pojo.Comment;
import cn.malong.blog.service.CommentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author malong
 * @Date 2021-10-31 15:55:01
 */
@RestController
@RequestMapping("/comment")
public class CommentsController {

    @Autowired
    private CommentsService commentsServiceImpl;

    @RequestMapping("/dataLimit")
    public String getCommentDataLimit(int page, int limit, String content, String nickname) {
        if (nickname == null && content == null) {
            return commentsServiceImpl.getCommentDataLimit(page, limit);
        }
        // 下面这个主要是用于查询操作
        return commentsServiceImpl.getCommentDataByNicknameAndContent(page, limit, nickname, content);
    }

    @DeleteMapping("/delete")
    public String deleteComment(int id) {
        return commentsServiceImpl.commentDelete(id);
    }

    @DeleteMapping("/batchDelete")
    public String batchDeleteComments(int[] ids) {
        return commentsServiceImpl.commentBatchDelete(ids);
    }

    @PutMapping("/commentBlog")
    public String commentBlog(@RequestBody Comment comment) {
        return commentsServiceImpl.commentBlog(comment);
    }

    @PutMapping("/replyTopComment/{parentCommentId}/{repliedUserId}/{blogId}/{content}")
    public String replyTopComment(@PathVariable int parentCommentId, @PathVariable int repliedUserId,
                                  @PathVariable int blogId, @PathVariable String content) {
        return commentsServiceImpl.replyTopComment(parentCommentId, repliedUserId, blogId, content);
    }

    @GetMapping("/getCommentsByBlogId/{blogId}")
    public String getCommentsByBlogId(@PathVariable("blogId") int blogId) {
        return commentsServiceImpl.getCommentByBlogId(blogId);
    }
}
