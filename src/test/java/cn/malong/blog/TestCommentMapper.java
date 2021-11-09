package cn.malong.blog;

import cn.malong.blog.dao.CommentsMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Csy
 * @Classname TestCommentMapper
 * @date 2021-11-08 9:33
 * @Description TODO
 */
@Transactional
@SpringBootTest
public class TestCommentMapper {

    @Autowired
    CommentsMapper commentsMapper;

    @Test
    void testCountComment(){
        int ret = commentsMapper.countComment();
        System.out.println(ret);
    }
}
