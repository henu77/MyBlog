package cn.malong.blog.dao;

import cn.malong.blog.pojo.Tag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Csy
 * @Classname TagsMapper
 * @date 2021-10-31 22:42
 * @Description TODO
 */
@Mapper
@Repository
public interface TagsMapper {

    List<Tag> getTagsByLimit(@Param("startIndex") int startIndex,@Param("pageSize") int pageSize);
}
