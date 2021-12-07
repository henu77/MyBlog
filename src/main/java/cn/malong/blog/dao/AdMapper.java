package cn.malong.blog.dao;

import cn.malong.blog.pojo.Advertisement;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AdMapper {
    int insertAnAd(Advertisement advertisement);

    List<Advertisement> getAdDataByLimit(int startIndex, int limit);

    int allAdCount();

    int deleteComment(int id);

    int deleteCommentsByBatch(int[] ids);

    Advertisement queryAdById(int id);

    int updateState(@Param("id") int id, @Param("state") String state);

    List<Advertisement> queryAllAdsIsPass();

}
