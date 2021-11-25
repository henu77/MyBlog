package cn.malong.blog.dao;

import cn.malong.blog.pojo.Blog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface BlogsMapper {

    List<Blog> getBlogsByLimit(@Param("startIndex") int startIndex, @Param("pageSize") int pageSize);

    List<Blog> getBlogsByLimitForFont(@Param("typeId") int typeId, @Param("startIndex") int startIndex, @Param("pageSize") int pageSize);

    List<Blog> getBlogsByLimitByTitle(@Param("startIndex") int startIndex, @Param("pageSize") int pageSize, @Param("title") String title);

    List<Blog> getBlogsByLimitByUser(@Param("startIndex") int startIndex, @Param("pageSize") int pageSize, @Param("username") String username);

    List<Blog> getBlogsByLimitByTitleAndUser(@Param("startIndex") int startIndex, @Param("pageSize") int pageSize, @Param("title") String title, @Param("username") String username);

    Blog getBlogById(int id);

    int countAllBlogs();

    /**
     * 保存博客
     *
     * @param blog
     * @return
     * @author MaLong
     */
    int saveBlog(Blog blog);

    void addViews(int blogId);

    int removeBlogById(int id);

    int getAuthorByBlogId(int id);

    /**
     * 获得浏览量最高得5篇文章得id、title
     *
     * @return
     */
    List<Blog> getHotBlogs();

    List<Blog> getTopBlogs();
}
