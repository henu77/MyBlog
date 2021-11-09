package cn.malong.blog.service.impl;

import cn.malong.blog.dao.CommentsMapper;
import cn.malong.blog.pojo.Comment;
import cn.malong.blog.pojo.UserInfo;
import cn.malong.blog.service.CommentsService;
import cn.malong.blog.utils.ResponseUtil;
import cn.malong.blog.utils.StaticString;
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
        return commentDataToJson(commentDataByLimit);
    }

    @Override
    public String getCommentDataByNicknameAndContent(int page,int limit,String nickname,String content){
        int startIndex = (page-1)*limit;
        if(null!=nickname){
            nickname = nickname.replace(" ","");
            if("".equals(nickname)){
                nickname = null;
            }
        }
        if(null!=content){
            content = content.replace(" ","");
            if("".equals(content)){
                content = null;
            }
        }
        List<Comment> comments = commentsMapper.getCommentDataByNicknameAndContent(startIndex,limit,nickname,content);
        return commentDataToJson(comments);
    }

    @Override
    public String commentDelete(int id) {
        if(!isHavingAuthority()){
            ResponseUtil<String> json = new ResponseUtil<>();
            json.setCode(0);
            json.setMsg("您的权限太低，无法删除");
            return json.toString();
        }
        int ret = commentsMapper.deleteComment(id);
        ResponseUtil<String> json = new ResponseUtil<>();
        if(ret>0){
            json.setCode(1);
            json.setMsg("删除成功");
        }else{
            json.setCode(0);
            json.setMsg("删除失败");
        }
        return json.toString();
    }

    @Override
    public String commentBatchDelete(int[] ids) {
        if(!isHavingAuthority()){
            ResponseUtil<String> json = new ResponseUtil<>();
            json.setCode(0);
            json.setMsg("您的权限太低，无法删除");
            return json.toString();
        }
        int ret = commentsMapper.deleteCommentsByBatch(ids);
        ResponseUtil<String> json = new ResponseUtil<>();
        if(ret>0){
            json.setCode(1);
            json.setMsg("删除成功");
        }else{
            json.setCode(0);
            json.setMsg("删除失败");
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

    private String commentDataToJson(List<Comment> commentData) {
        ResponseUtil<Comment> json = new ResponseUtil<>();
        if (null == commentData) {
            json.setCode(1);
            json.setMsg("获取用户信息失败");
        } else if (commentData.isEmpty()) {
            json.setCode(1);
            json.setMsg("用户信息为空");
        } else {
            json.setCode(0);
            json.setCount(commentsMapper.countComment());
            json.setMsg("获取用户信息成功");
            json.setData(commentData);
        }
        return json.toString();
    }
}
