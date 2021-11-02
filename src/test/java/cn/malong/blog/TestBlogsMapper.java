package cn.malong.blog;

import cn.malong.blog.dao.BlogsMapper;
import cn.malong.blog.pojo.Blog;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author Csy
 * @Classname TestBlogsMapper
 * @date 2021-11-01 8:20
 * @Description TODO
 */
@SpringBootTest
public class TestBlogsMapper {

    @Autowired
    private BlogsMapper blogsMapper;

    @Test
    public void testGetBlogsByLimit(){
        List<Blog> blogs = blogsMapper.getBlogsByLimit(0,10);
        if(null != blogs){
            for (Blog blog : blogs) {
                System.out.println(blog);
            }
        }
    }

    @Test
    public void testGetBlogsByLimitByTitle(){
        List<Blog> blogs = blogsMapper.getBlogsByLimitByTitle(0,10,"工作");
        if(null!=blogs){
            for (Blog blog : blogs) {
                System.out.println(blog);
            }
        }
    }

    @Test
    public void testGetBlogByLimitByUser(){
        List<Blog> blogs = blogsMapper.getBlogsByLimitByUser(0,10,"root");
        if(null != blogs){
            System.out.println(blogs);
        }
    }

    @Test
    public void testGetBlogsByLimitByTitleAndUser(){
        List<Blog> blogs = blogsMapper.getBlogsByLimitByTitleAndUser(0,10,"工作","roott");
        if(null != blogs){
            for (Blog blog : blogs) {
                System.out.println(blog);
            }
        }
    }
}
