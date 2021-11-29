package cn.malong.blog.dao;

import cn.malong.blog.pojo.Province;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author marlone
 * @Date 2021/11/26 22:47
 */
@Mapper
@Repository
public interface ProvinceMapper {
    int addCount(@Param("provinceName") String provinceName);
    List<Province> getAllData();
}
