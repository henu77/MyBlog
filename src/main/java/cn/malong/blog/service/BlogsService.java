package cn.malong.blog.service;

import cn.malong.blog.pojo.Blog;

/**
 * @author Csy
 * @Classname BlogsService
 * @date 2021-11-01 8:41
 * @Description TODO
 */
public interface BlogsService {
    String getBlogsByLimit(int page,int limit);

    String getBlogsByLimit(int page,int limit,String title,String user);

    String postArticle(Blog blog);

    String getBlogsByPage(int page);

    String saveArticle(Blog blog);

    String removeBlogById(int id);
}
