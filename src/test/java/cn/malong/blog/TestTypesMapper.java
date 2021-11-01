package cn.malong.blog;

import cn.malong.blog.dao.TypesMapper;
import cn.malong.blog.pojo.Type;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author Csy
 * @Classname TestTypesMapper
 * @date 2021-10-31 22:14
 * @Description TODO
 */

@SpringBootTest
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
        Type type = typesMapper.getType(1);
        if(null != type){
            System.out.println(type);
        }
    }

}
