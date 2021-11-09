package cn.malong.blog.controller;

import cn.malong.blog.service.CommentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public String getCommentDataLimit(int page, int limit,String content,String nickname) {
        if(nickname==null&&content==null){
            return commentsServiceImpl.getCommentDataLimit(page,limit);
        }
        // 下面这个主要是用于查询操作
        return commentsServiceImpl.getCommentDataByNicknameAndContent(page,limit,nickname,content);
    }

    @DeleteMapping("/delete")
    public String deleteComment(int id){
        return commentsServiceImpl.commentDelete(id);
    }

    @DeleteMapping("/batchDelete")
    public String batchDeleteComments(int[] ids){
        System.out.println("进来了吗");
        return commentsServiceImpl.commentBatchDelete(ids);
    }
}
