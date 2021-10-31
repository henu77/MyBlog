package cn.malong.blog;

import cn.malong.blog.dao.TagsMapper;
import cn.malong.blog.pojo.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author Csy
 * @Classname TestTagsMapper
 * @date 2021-10-31 22:47
 * @Description TODO
 */
@SpringBootTest
public class TestTagsMapper {

    @Autowired
    TagsMapper tagsMapper;

    @Test
    public void testGetTagsByLimit(){
        System.out.println(tagsMapper);
        List<Tag> tags = tagsMapper.getTagsByLimit(1,10);
        if(tags!=null){
            for (Tag tag : tags) {
                System.out.println(tag);
            }
        }
    }
}
