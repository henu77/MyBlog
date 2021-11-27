package cn.malong.blog.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author marlone
 * @Date 2021/11/26 22:47
 */
@Mapper
@Repository
public interface ProvinceMapper {
    int addCount(@Param("provinceName") String provinceName);
}
