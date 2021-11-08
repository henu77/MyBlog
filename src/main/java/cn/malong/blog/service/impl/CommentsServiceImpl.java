package cn.malong.blog.service.impl;

import cn.malong.blog.dao.CommentsMapper;
import cn.malong.blog.pojo.Comment;
import cn.malong.blog.pojo.UserInfo;
import cn.malong.blog.service.CommentsService;
import cn.malong.blog.utils.ResponseUtil;
import cn.malong.blog.utils.StaticVariable;
import cn.malong.blog.utils.servlet.ServletUtil;
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

    @Override
    public String commentDelete(int id) {
        return null;
    }

    private UserInfo getUserInfoFromSession() {
        UserInfo userInfo = (UserInfo) ServletUtil.getSession().getAttribute("userInfo");
        return userInfo;
    }

    private boolean isHavingAuthority() {
        UserInfo userInfoFromSession = getUserInfoFromSession();
        if (userInfoFromSession.getRole().equals(StaticVariable.ROLE_USER)) {
            return false;
        } else if (userInfoFromSession.getRole().equals(StaticVariable.ROLE_ADMIN)
                || userInfoFromSession.getRole().equals(StaticVariable.ROLE_ROOT)) {
            return true;
        } else {
            return false;
        }
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
