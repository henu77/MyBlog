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

    List<Blog> getBlogsByLimitByTitle(@Param("startIndex") int startIndex, @Param("pageSize") int pageSize, @Param("title") String title);

    List<Blog> getBlogsByLimitByUser(@Param("startIndex") int startIndex, @Param("pageSize") int pageSize, @Param("username") String username);

    List<Blog> getBlogsByLimitByTitleAndUser(@Param("startIndex") int startIndex, @Param("pageSize") int pageSize, @Param("title") String title, @Param("username") String username);

    Blog getBlogById(int id);
    int countAllBlogs();
    /**
     * 保存博客
     * @author MaLong
     * @param blog
     * @return
     */
    int saveBlog(Blog blog);
}
