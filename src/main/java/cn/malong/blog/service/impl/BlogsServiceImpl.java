package cn.malong.blog.service.impl;

import cn.malong.blog.dao.BlogsMapper;
import cn.malong.blog.dao.TypesMapper;
import cn.malong.blog.pojo.Blog;
import cn.malong.blog.pojo.Type;
import cn.malong.blog.pojo.UserInfo;
import cn.malong.blog.service.BlogsService;
import cn.malong.blog.utils.DateUtils;
import cn.malong.blog.utils.ResponseUtil;
import cn.malong.blog.utils.servlet.ServletUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author Csy
 * @Classname BlogsServiceImpl
 * @date 2021-11-01 8:42
 * @Description TODO
 */
@Service
public class BlogsServiceImpl implements BlogsService {


    @Autowired
    private BlogsMapper blogsMapper;

    @Autowired
    private TypesMapper typesMapper;

    @Override
    public String getBlogsByLimit(int page, int limit) {
        int startIndex = (page - 1) * limit;
        List<Blog> blogData = blogsMapper.getBlogsByLimit(startIndex, limit);

        if (null == blogData) {
            System.out.println("数据为空");
            return "";
        }

        // 查看是否正确
//        System.out.println(blogDataToJson(blogData));

        return (blogDataToJson(blogData));
    }

    @Override
    public String getBlogsByLimit(int page, int limit, String title, String user) {
        int startIndex = (page - 1) * limit;
        List<Blog> blogData = null;
        if (isEmpty(title) && isEmpty(user)) {
            return getBlogsByLimit(page, limit);
        } else if (isEmpty(user)) {
            blogData = blogsMapper.getBlogsByLimitByTitle(startIndex, limit, title);
        } else if (isEmpty(title)) {
            blogData = blogsMapper.getBlogsByLimitByUser(startIndex, limit, user);
        } else {
            blogData = blogsMapper.getBlogsByLimitByTitleAndUser(startIndex, limit, title, user);
        }
        return blogDataToJson(blogData);
    }

    public String getBlogsByPage(int page){
        // 每次下拉时最多加载2条
        return getBlogsByLimit(page,2);
    }

    /**
     * @param blog
     * @return
     * @auther MaLong
     */
    @Override
    public String postArticle(Blog blog) {
        UserInfo userInfo = (UserInfo) ServletUtil.getSession().getAttribute("userInfo");
        blog.setUserId(userInfo);
        Type typeById = typesMapper.getTypeById(blog.getTypeId().getId());
        blog.setTypeId(typeById);
        Date date = new Date();
        blog.setCreatTime(date);
        blog.setUpdateTime(date);
        blog.setViews(0);
        blog.setDescription("无");
        blog.setFirstPicture("1111");
        int result = blogsMapper.saveBlog(blog);
        ResponseUtil<String> json = new ResponseUtil<>();
        if (result > 0) {
            json.setCode(1);
            json.setMsg("发布成功");
        } else {
            json.setCode(0);
            json.setMsg("发布失败");
        }
        return json.toString();
    }

    private boolean isEmpty(String s) {
        if (null == s) {
            return true;
        }
        if ("".equals(s)) {
            return true;
        }
        if ("".equals(s.trim())) {
            return true;
        }
        return false;
    }

    private String blogDataToJson(List<Blog> blogData) {
        ResponseUtil<Map> json = new ResponseUtil<>();
        if (null == blogData) {
            json.setCode(1);
            json.setMsg("获取用户信息失败");
        } else if (blogData.isEmpty()) {
            json.setCode(1);
            json.setMsg("用户信息为空");
        } else {
            json.setCode(0);
            json.setCount(blogData.size());
            json.setMsg("获取用户信息成功");

            // 处理返回json格式复合表格要求
            List<Map> jsonMap = new LinkedList<>();
            for (Blog blog : blogData) {
                Map<String, Object> temp = new HashMap<>();
                temp.put("id", blog.getId());
                temp.put("title", blog.getTitle());
                temp.put("type", blog.getTypeId().getName());
                temp.put("user", blog.getUserId().getUsername());
                temp.put("creatTime", DateUtils.d2t(blog.getCreatTime()).toString());
                temp.put("updateTime", DateUtils.d2t(blog.getUpdateTime()).toString());
                jsonMap.add(temp);
            }
            json.setData(jsonMap);
        }
        return json.toString();
    }
}