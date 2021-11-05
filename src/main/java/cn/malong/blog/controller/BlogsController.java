package cn.malong.blog.controller;

import cn.malong.blog.pojo.Blog;
import cn.malong.blog.service.BlogsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Csy
 * @Classname BlogsController
 * @date 2021-11-01 13:06
 * @Description TODO
 */
@RestController
@RequestMapping("/blog")
public class BlogsController {

    @Autowired
    private BlogsService blogsService;

    @RequestMapping("/dataLimit")
    public String getBlogsByLimit(int page, int limit,String title,String username) {
//        System.out.println(blogsService.getBlogsByLimit(page, limit));
        return blogsService.getBlogsByLimit(page, limit,title,username);
    }

    @PostMapping("/postArticle")
    public String postArticle(@RequestBody Blog blog) {
        return blogsService.postArticle(blog);
    }

    @RequestMapping("/more")
    public String getBlogsByPage(int page){
        return "";
    }

}
