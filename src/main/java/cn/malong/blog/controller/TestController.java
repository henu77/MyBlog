package cn.malong.blog.controller;

import cn.malong.blog.dao.UserMapper;
import cn.malong.blog.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class TestController {

    @Autowired
    UserMapper userMapper;

    public TestController() {
    }

    @GetMapping({"/getAlluser"})
    public String getAllUser() {
        List<User> allUsers = this.userMapper.getAllUsers();
        return allUsers.toString();
    }

    @GetMapping({"/getUserById/{id}"})
    public String getUserById(@PathVariable("id") int id) {
        User userById = this.userMapper.getUserById(id);
        return userById.toString();
    }

}
