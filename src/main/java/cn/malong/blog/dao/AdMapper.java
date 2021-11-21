package cn.malong.blog.dao;

import cn.malong.blog.pojo.Advertisement;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AdMapper {
    int insertAnAd(Advertisement advertisement);

    List<Advertisement> getAdDataByLimit(int startIndex, int limit);

    int allAdCount();
}
