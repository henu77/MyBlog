package cn.malong.blog.dao;

import cn.malong.blog.pojo.Message;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author marlone
 * @Date 2021/12/8 18:02
 */
@Mapper
@Repository
public interface MessageMapper {

    int addAMessage(Message message);

    List<Message> queryMessageByPage(@Param("startIndex") int startIndex,@Param("limit") int limit);

    int countAllMessages();
}
