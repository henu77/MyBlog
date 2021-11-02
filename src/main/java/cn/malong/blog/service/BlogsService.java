package cn.malong.blog.service;

/**
 * @author Csy
 * @Classname BlogsService
 * @date 2021-11-01 8:41
 * @Description TODO
 */
public interface BlogsService {
    String getBlogsByLimit(int page,int limit);

    String getBlogsByLimit(int page,int limit,String title,String user);
}
