package cn.malong.blog.dao;

import cn.malong.blog.pojo.Blog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Mapper
@Repository
public interface BlogsMapper {

    List<Blog> getBlogsByLimit(@Param("startIndex")int startIndex,@Param("pageSize")int pageSize);
}
