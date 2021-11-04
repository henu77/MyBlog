package cn.malong.blog;

import cn.malong.blog.dao.BlogsMapper;
import cn.malong.blog.dao.UserInfoMapper;
import cn.malong.blog.pojo.Blog;
import cn.malong.blog.pojo.Type;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
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
    @Autowired
    private UserInfoMapper userInfoMapper;

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

    @Test
    public void testSaveBlog() {
        List<Blog> blogs = blogsMapper.getBlogsByLimitByTitleAndUser(0, 10, "工作", "root");
        if (null != blogs) {
            for (Blog blog : blogs) {
                System.out.println(blog);
            }
        }
        System.out.println();
        System.out.println();
        System.out.println();
        Blog blog = new Blog();
        blog.setContent("你好你好");
        blog.setCreatTime(new Date());
        blog.setUpdateTime(new Date());
        blog.setFlag("原创");
        blog.setDescription("wuwuwu");
        blog.setViews(10000);
        blog.setTypeId(new Type(1, "学习"));
        blog.setUserId(userInfoMapper.getUserInfoById(2));
        blog.setTitle("测试");
        blog.setShareStatement(true);
        blog.setRecommend(true);
        blog.setPublished(true);
        blog.setCommentabled(true);
        blog.setFirstPicture("111111");
        blogsMapper.saveBlog(blog);
    }

}
