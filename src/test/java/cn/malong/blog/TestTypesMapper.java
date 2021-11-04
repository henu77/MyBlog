package cn.malong.blog;

import cn.malong.blog.dao.TypesMapper;
import cn.malong.blog.pojo.Type;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Csy
 * @Classname TestTypesMapper
 * @date 2021-10-31 22:14
 * @Description TODO
 */

@SpringBootTest
@Transactional
public class TestTypesMapper {
    @Autowired
    TypesMapper typesMapper;

    @Test
    public void testGetTypesByLimit(){
        System.out.println(typesMapper);
        List<Type> types = typesMapper.getTypesByLimit(1,10);
        if(types!=null){
            for (Type type : types) {
                System.out.println(type);
            }
        }

    }

    @Test
    public void testGetType(){
        Type type = typesMapper.getTypeById(1);
        if(null != type){
            System.out.println(type);
        }
    }

    @Test
    @Rollback
    public void testAddType(){
        Type bean = new Type();
        bean.setName("SpringBoot");
        int ret = typesMapper.addType(bean);
        if(ret == 1){
            System.out.println("添加成功");
            System.out.println(bean);
        }

    }
}
