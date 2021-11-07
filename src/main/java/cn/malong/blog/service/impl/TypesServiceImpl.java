package cn.malong.blog.service.impl;

import cn.malong.blog.dao.TypesMapper;
import cn.malong.blog.pojo.Type;
import cn.malong.blog.pojo.UserInfo;
import cn.malong.blog.service.TypesService;
import cn.malong.blog.utils.ResponseUtil;
import cn.malong.blog.utils.StaticString;
import cn.malong.blog.utils.servlet.ServletUtil;
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

    @Override
    public String typeAdd(Type type) {
        if (!isHavingAuthority()) {
            //用户权限为 user 时
            ResponseUtil<String> json = new ResponseUtil<>();
            json.setCode(0);
            json.setMsg("您的权限太低！");
            return json.toString();
        }
        ResponseUtil<String> json = new ResponseUtil<>();
        int success = typesMapper.addType(type);
        if(success>0){
            json.setCode(1);
            json.setMsg("添加成功");
        }else{
            json.setCode(0);
            json.setMsg("添加失败");
        }
        System.out.println(json);
        return json.toString();
    }

    @Override
    public String typeUpdate(Type type) {
        if(!isHavingAuthority()){
            ResponseUtil<String> json = new ResponseUtil<>();
            json.setCode(0);
            json.setMsg("您的权限太低，无法修改");
            return json.toString();
        }
        ResponseUtil<String> json = new ResponseUtil<>();
        int success = typesMapper.updateType(type);
        if(success>0){
            json.setCode(1);
            json.setMsg("修改成功");
        }else{
            json.setCode(0);
            json.setMsg("修改失败");
        }
        return json.toString();
    }

    @Override
    public String typeDelete(int id) {
        if(!isHavingAuthority()){
            ResponseUtil<String> json = new ResponseUtil<>();
            json.setCode(0);
            json.setMsg("您的权限太低，无法修改");
            return json.toString();
        }
        ResponseUtil<String> json = new ResponseUtil<>();
        int success = typesMapper.deleteType(id);
        if(success>0){
            json.setCode(1);
            json.setMsg("删除成功");
        }else{
            json.setCode(0);
            json.setMsg("删除失败");
        }
        return json.toString();
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

    private UserInfo getUserInfoFromSession() {
        UserInfo userInfo = (UserInfo) ServletUtil.getSession().getAttribute("userInfo");
        return userInfo;
    }

    private boolean isHavingAuthority() {
        UserInfo userInfoFromSession = getUserInfoFromSession();
        if (userInfoFromSession.getRole().equals(StaticString.ROLE_USER)) {
            return false;
        } else if (userInfoFromSession.getRole().equals(StaticString.ROLE_ADMIN)
                || userInfoFromSession.getRole().equals(StaticString.ROLE_ROOT)) {
            return true;
        } else {
            return false;
        }
    }
}
