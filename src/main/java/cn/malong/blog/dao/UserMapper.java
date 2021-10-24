package cn.malong.blog.dao;

import cn.malong.blog.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {
    List<User> getAllUsers();

    User getUserById(int id);
}
