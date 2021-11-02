package cn.malong.blog.controller;

import cn.malong.blog.service.CommentsService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public String getCommentDataLimit(int page, int limit) {

        return commentsServiceImpl.getCommentDataLimit(page, limit);
    }
}
