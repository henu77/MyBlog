package cn.malong.blog.service.impl;

import cn.malong.blog.dao.BlogsMapper;
import cn.malong.blog.dao.CommentsMapper;
import cn.malong.blog.dao.TypesMapper;
import cn.malong.blog.pojo.Blog;
import cn.malong.blog.pojo.Type;
import cn.malong.blog.pojo.UserInfo;
import cn.malong.blog.service.BlogsService;
import cn.malong.blog.utils.*;
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
    @Autowired
    private CommentsMapper commentsMapper;

    @Override
    public String getBlogsByLimit(int page, int limit) {
        int startIndex = (page - 1) * limit;
        List<Blog> blogData = blogsMapper.getBlogsByLimit(startIndex, limit);

        if (null == blogData) {
            System.out.println("数据为空");
            return "";
        }

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

    @Override
    public String getBlogsByPage(int page) {
        int limit = StaticVariable.FLOW_PAGE_SIZE;
        int startIndex = (page - 1) * limit;
        List<Blog> blogData = blogsMapper.getBlogsByLimitForFont(startIndex, limit);
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
                temp.put("flag", blog.getFlag());
                temp.put("type", blog.getTypeId().getName());
                temp.put("author", blog.getUserId().getNickname());
                Date updateTime = blog.getUpdateTime();
                temp.put("day", CalendarUtil.getDay(updateTime));
                temp.put("month", CalendarUtil.getMonth(updateTime));
                temp.put("year", CalendarUtil.getYear(updateTime));
                temp.put("comments", commentsMapper.countCommentsByBlogId(blog.getId()));
                temp.put("views", blog.getViews());
                temp.put("recommend", blog.isRecommend());
                temp.put("firstPic", blog.getFirstPicture());
                String tempString = blog.getContent();
                if (blog.getContent().length() >= 250) {
                    tempString = blog.getContent().substring(0, 250);
                }
                temp.put("content", MarkdownUtils.convert(tempString));
                jsonMap.add(temp);
            }
            json.setData(jsonMap);
            int countAllBlogs = blogsMapper.countAllBlogs();
            json.setPages((int) Math.ceil(1.0 * countAllBlogs / limit));
        }
        return json.toString();
    }

    /**
     * 保存博客
     *
     * @param blog
     * @return
     */
    @Override
    public String saveArticle(Blog blog) {
        initBlog(blog);
        blog.setPublished(false);
        int result = blogsMapper.saveBlog(blog);
        ResponseUtil<String> json = new ResponseUtil<>();
        if (result > 0) {
            json.setCode(1);
            json.setMsg("保存成功");
        } else {
            json.setCode(0);
            json.setMsg("保存失败");
        }
        return json.toString();
    }

    @Override
    public String removeBlogById(int id) {
        if (!UserServiceImpl.isHavingAuthority()) {
            //用户权限为 user 时
            ResponseUtil<String> json = new ResponseUtil<>();
            json.setCode(0);
            json.setMsg("您的权限太低！");
            return json.toString();
        }
        UserInfo nowUserInfo = UserServiceImpl.getUserInfoFromSession();
        if (nowUserInfo.getId() != blogsMapper.getAuthorByBlogId(id)) {
            //用户删除的博客的作者不是自己的时
            ResponseUtil<String> json = new ResponseUtil<>();
            json.setCode(0);
            json.setMsg("您不能删除别人的博客！");
            return json.toString();
        }
        int result = blogsMapper.removeBlogById(id);
        ResponseUtil<String> json = new ResponseUtil<>();
        if (result > 0) {
            json.setCode(1);
            json.setMsg("删除成功");
        } else {
            json.setCode(0);
            json.setMsg("删除失败");
        }
        return json.toString();
    }

    private void initBlog(Blog blog) {
        UserInfo userInfo = (UserInfo) ServletUtil.getSession().getAttribute("userInfo");
        blog.setUserId(userInfo);
        Type typeById = typesMapper.getTypeById(blog.getTypeId().getId());
        blog.setTypeId(typeById);
        Date date = new Date();
        blog.setCreatTime(date);
        blog.setUpdateTime(date);
        blog.setViews(0);
        blog.setDescription("无");
        blog.setFirstPicture(blog.getFirstPicture());
    }

    /**
     * @param blog
     * @return
     * @auther MaLong
     */
    @Override
    public String postArticle(Blog blog) {
        initBlog(blog);
        blog.setPublished(true);
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
                temp.put("isPublic", blog.isPublished() == true ? "公开" : "私有");
                temp.put("creatTime", DateUtils.dateToString(blog.getCreatTime()));
                temp.put("updateTime", DateUtils.dateToString(blog.getUpdateTime()));
                jsonMap.add(temp);
            }
            json.setData(jsonMap);
        }
        return json.toString();
    }
}
