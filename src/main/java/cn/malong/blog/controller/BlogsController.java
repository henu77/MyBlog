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
    public String getBlogsByLimit(int page, int limit, String title, String username) {
        return blogsService.getBlogsByLimit(page, limit, title, username);
    }

    @PostMapping("/postArticle")
    public String postArticle(@RequestBody Blog blog) {
        return blogsService.postArticle(blog);
    }

    @PostMapping("/saveArticle")
    public String saveArticle(@RequestBody Blog blog) {
        return blogsService.saveArticle(blog);
    }

    @GetMapping("/more")
    public String getBlogsByPage(int typeId, int page) {
        return blogsService.getBlogsByPage(typeId, page);
    }

    @DeleteMapping("/remove/{id}")
    public String removeBlogById(@PathVariable("id") int id) {
        return blogsService.removeBlogById(id);
    }
}
