package cn.malong.blog.service.impl;

import cn.malong.blog.dao.CommentsMapper;
import cn.malong.blog.pojo.Comment;
import cn.malong.blog.pojo.UserInfo;
import cn.malong.blog.service.CommentsService;
import cn.malong.blog.utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author malong
 * @Date 2021-10-31 15:56:44
 */
@Service
public class CommentsServiceImpl implements CommentsService {

    @Autowired
    private CommentsMapper commentsMapper;

    @Override
    public String getCommentDataLimit(int page, int limit) {
        int startIndex = (page - 1) * limit;
        List<Comment> commentDataByLimit = commentsMapper.getCommentDataByLimit(startIndex, limit);
        return userDataToJson(commentDataByLimit);
    }

    private String userDataToJson(List<Comment> commentData) {
        ResponseUtil<Comment> json = new ResponseUtil<>();
        if (null == commentData) {
            json.setCode(1);
            json.setMsg("获取用户信息失败");
        } else if (commentData.isEmpty()) {
            json.setCode(1);
            json.setMsg("用户信息为空");
        } else {
            json.setCode(0);
            json.setCount(commentData.size());
            json.setMsg("获取用户信息成功");
            json.setData(commentData);
        }
        return json.toString();
    }
}
