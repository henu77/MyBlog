package cn.malong.blog.service.impl;

import cn.malong.blog.dao.TypesMapper;
import cn.malong.blog.pojo.Type;
import cn.malong.blog.service.TypesService;
import cn.malong.blog.utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Csy
 * @Classname TypesServiceImpl
 * @date 2021-11-04 17:21
 * @Description TODO
 */

@Service
public class TypesServiceImpl implements TypesService {

    @Autowired
    private TypesMapper typesMapper;

    @Override
    public String getTypesByLimit(int page,int limit) {
        int startIndex = (page-1)*limit;
        List<Type> typeData = typesMapper.getTypesByLimit(startIndex,limit);
        return typeDataToJson(typeData);
    }

    private String typeDataToJson(List<Type> typeData) {
        ResponseUtil<Type> json = new ResponseUtil<>();
        if (null == typeData) {
            json.setCode(1);
            json.setMsg("获取用户信息失败");
        } else if (typeData.isEmpty()) {
            json.setCode(1);
            json.setMsg("用户信息为空");
        } else {
            json.setCode(0);
            json.setCount(typeData.size());
            json.setMsg("获取用户信息成功");
            json.setData(typeData);
        }
        return json.toString();
    }
}
