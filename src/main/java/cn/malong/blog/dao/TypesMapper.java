package cn.malong.blog.dao;

import cn.malong.blog.pojo.Type;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Classname TypesMapper
 * @author Csy
 * @date 2021-10-31
 * @Description TODO
 */

@Mapper
@Repository
public interface TypesMapper {

    List<Type> getTypesByLimit(@Param("startIndex") int startIndex, @Param("pageSize") int pageSize);

    Type getType(@Param("typeId")int typeId);
}