package cn.malong.blog.service.impl;

import cn.malong.blog.dao.BlogsMapper;
import cn.malong.blog.dao.CommentsMapper;
import cn.malong.blog.dao.UserInfoMapper;
import cn.malong.blog.pojo.Comment;
import cn.malong.blog.pojo.UserInfo;
import cn.malong.blog.service.CommentsService;
import cn.malong.blog.utils.DateUtils;
import cn.malong.blog.utils.ResponseUtil;
import cn.malong.blog.utils.StaticVariable;
import cn.malong.blog.utils.servlet.ServletUtil;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author malong
 * @Date 2021-10-31 15:56:44
 */
@Service
public class CommentsServiceImpl implements CommentsService {

    @Autowired
    private CommentsMapper commentsMapper;
    @Autowired
    private BlogsMapper blogsMapper;
    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public String getCommentDataLimit(int page, int limit) {
        int startIndex = (page - 1) * limit;
        List<Comment> commentDataByLimit = commentsMapper.getCommentDataByLimit(startIndex, limit);
        return commentDataToJson(commentDataByLimit);
    }

    @Override
    public String getCommentDataByNicknameAndContent(int page, int limit, String nickname, String content) {
        int startIndex = (page - 1) * limit;
        if (null != nickname) {
            nickname = nickname.replace(" ", "");
            if ("".equals(nickname)) {
                nickname = null;
            }
        }
        if (null != content) {
            content = content.replace(" ", "");
            if ("".equals(content)) {
                content = null;
            }
        }
        List<Comment> comments = commentsMapper.getCommentDataByNicknameAndContent(startIndex, limit, nickname, content);
        return commentDataToJson(comments);
    }

    @Override
    public String commentDelete(int id) {
        if (!isHavingAuthority()) {
            ResponseUtil<String> json = new ResponseUtil<>();
            json.setCode(0);
            json.setMsg("您的权限太低，无法删除");
            return json.toString();
        }
        int ret = commentsMapper.deleteComment(id);
        ResponseUtil<String> json = new ResponseUtil<>();
        if (ret > 0) {
            json.setCode(1);
            json.setMsg("删除成功");
        } else {
            json.setCode(0);
            json.setMsg("删除失败");
        }
        return json.toString();
    }

    @Override
    public String commentBatchDelete(int[] ids) {
        if (!isHavingAuthority()) {
            ResponseUtil<String> json = new ResponseUtil<>();
            json.setCode(0);
            json.setMsg("您的权限太低，无法删除");
            return json.toString();
        }
        int ret = commentsMapper.deleteCommentsByBatch(ids);
        ResponseUtil<String> json = new ResponseUtil<>();
        if (ret > 0) {
            json.setCode(1);
            json.setMsg("删除成功");
        } else {
            json.setCode(0);
            json.setMsg("删除失败");
        }
        return json.toString();
    }

    @Override
    public String commentBlog(Comment comment) {
        UserInfo userInfoFromSession = getUserInfoFromSession();
        ResponseUtil<String> json = new ResponseUtil<>();
        if (null == userInfoFromSession) {
            json.setCode(0);
            json.setMsg("请先登录！");
        } else {
            int repliedUserId = blogsMapper.getAuthorByBlogId(comment.getBlogId());
            UserInfo userInfoById = userInfoMapper.getUserInfoById(repliedUserId);
            comment.setCreateTime(new Date());
            comment.setUserId(userInfoFromSession);
            comment.setRepliedUserId(userInfoById);
            int result = commentsMapper.insertAComment(comment);
            if (result > 0) {
                json.setCode(1);
                json.setMsg("评论成功!");
                List<String> data = new LinkedList<>();
                String resultStr = "";
                String projectPath = ServletUtil.getRequest().getContextPath();
                String avatar = "";
                avatar = userInfoFromSession.getAvatar();
                resultStr = getTopCommentHtml(comment, resultStr, projectPath, avatar);
                resultStr +=
                        "<div class=\"replycontainer layui-hide\">\n" +
                                "   <form class=\"layui-form\" action=\"\">\n" +
                                "       <input type=\"hidden\"  name=\"parentCommentId\" value>\n" +
                                "       <input type=\"hidden\"  name=\"repliedUserId\" value>" +
                                "       <input type=\"hidden\" name=\"blogId\"\n" +
                                "           value=\"" + comment.getBlogId() + "\">\n" +
                                "       <div class=\"layui-form-item\">\n" +
                                "           <textarea name=\"content\" lay-verify=\"required\"\n" +
                                "               class=\"layui-textarea\"\n" +
                                "               style=\"min-height:80px;\"></textarea>\n" +
                                "       </div>\n" +
                                "       <div class=\"layui-form-item\">\n" +
                                "           <button id=\"topCommentBut" + comment.getId() + "\"" +
                                " class=\"layui-btn layui-btn-normal layui-btn-xs\"\n" +
                                "  lay-submit" +
                                "               lay-filter=\"replyTopComment\">\n" +
                                "               提交\n" +
                                "           </button>\n" +
                                "       </div>\n" +
                                "   </form>\n" +
                                "</div>";
                resultStr += "</li>";
                data.add(resultStr);
                json.setData(data);
            } else {
                json.setCode(0);
                json.setMsg("评论失败!");
            }
        }
        return json.toString();
    }

    @NotNull
    private String getTopCommentHtml(Comment comment, String resultStr, String projectPath, String avatar) {
        if (!avatar.startsWith("D:") && !avatar.startsWith("/linux")) {
            avatar = "/linux" + avatar;
            comment.getUserId().setAvatar(avatar);
        }
        resultStr += "<li class=\"zoomIn article\">";
        resultStr += "<div class=\"comment-parent\">";
        resultStr += "<a name=\"remark-1\"></a>";
        resultStr += "<img src=\"" + projectPath + "/file/getImg/" + comment.getUserId().getAvatar() + "\"/>";
        resultStr += "<div class=\"info\">";
        resultStr += "<span class=\"username\">" + comment.getUserId().getNickname() + "</span>";
        resultStr += "</div>";
        resultStr += "<div class=\"comment-content\">" + comment.getContent() + "</div>";
        resultStr += "<p class=\"info info-footer\">\n" +
                "<span class=\"comment-time\">" + DateUtils.dateToString(comment.getCreateTime()) + "</span>\n" +
                "<a href=\"javascript:;\" class=\"btn-reply\" " +
                "data-parentCommentId=\"" + comment.getId() + "\" " +
                "data-repliedUserNickname=\"" + comment.getUserId().getNickname() + "\" " +
                "data-repliedUserId=\"" + comment.getUserId().getId() + "\">回复</a>\n" +
                "</p>";
        resultStr += "</div>";
        return resultStr;
    }

    @Override
    public String getCommentByBlogId(int blogId) {
        List<Comment> allComments = commentsMapper.getAllCommentsByBlogId(blogId);
        //处理不同系统下头像路径问题
        transformAvatarPath(allComments);
        ResponseUtil<String> json = new ResponseUtil<>();
        if (allComments.size() > 0) {
            json.setCode(1);
            json.setMsg("获取评论成功");
            List<String> data = new LinkedList<>();
            String result = "";
            String projectPath = ServletUtil.getRequest().getContextPath();
            String avatar = "";
            String avatar2 = "";
            for (Comment topComment :
                    allComments) {
                avatar = topComment.getUserId().getAvatar();
                result = getTopCommentHtml(topComment, result, projectPath, avatar);
                if (topComment.getChildComments().size() > 0) {
                    result += "<hr class=\"commentHr\">";
                    for (Comment childComment :
                            topComment.getChildComments()) {
                        avatar2 = childComment.getUserId().getAvatar();
                        if (!avatar2.startsWith("D:") && !avatar2.startsWith("/linux")) {
                            avatar2 = "/linux" + avatar2;
                            childComment.getUserId().setAvatar(avatar2);
                        }
                        result = getChildCommentHtml(childComment, result, projectPath, topComment.getUserId());
                    }
                }
                result +=
                        "<div class=\"replycontainer layui-hide\">\n" +
                                "   <form class=\"layui-form\" action=\"\">\n" +
                                "       <input type=\"hidden\"  name=\"parentCommentId\" value>\n" +
                                "       <input type=\"hidden\"  name=\"repliedUserId\" value>" +
                                "       <input type=\"hidden\" name=\"blogId\"\n" +
                                "           value=\"" + blogId + "\">\n" +
                                "       <div class=\"layui-form-item\">\n" +
                                "           <textarea name=\"content\" lay-verify=\"required\"\n" +
                                "               class=\"layui-textarea\"\n" +
                                "               style=\"min-height:80px;\"></textarea>\n" +
                                "       </div>\n" +
                                "       <div class=\"layui-form-item\">\n" +
                                "           <button id=\"topCommentBut" + topComment.getId() + "\"" +
                                " class=\"layui-btn layui-btn-normal layui-btn-xs\"\n" +
                                "  lay-submit" +
                                "               lay-filter=\"replyTopComment\">\n" +
                                "               提交\n" +
                                "           </button>\n" +
                                "       </div>\n" +
                                "   </form>\n" +
                                "</div>";
                result += "</li>";
            }
            data.add(result);
            json.setData(data);
        } else {
            json.setCode(0);
            json.setMsg("没有评论");
        }
        return json.toString();
    }

    @Override
    public String replyTopComment(int parentCommentId, int repliedUserId, int blogId, String content) {
        Comment comment = new Comment();
        comment.setParentCommentId(parentCommentId);
        comment.setRepliedUserId(userInfoMapper.getUserInfoById(repliedUserId));
        comment.setBlogId(blogId);
        comment.setContent(content);
        UserInfo userInfoFromSession = getUserInfoFromSession();
        ResponseUtil<String> json = new ResponseUtil<>();
        if (null == userInfoFromSession) {
            json.setCode(0);
            json.setMsg("请先登录！");
        } else {
            UserInfo userInfoById = userInfoMapper.getUserInfoById(repliedUserId);
            comment.setCreateTime(new Date());
            comment.setUserId(userInfoFromSession);
            int result = commentsMapper.insertAComment(comment);
            if (result > 0) {
                json.setCode(1);
                json.setMsg("评论成功!");
                List<String> data = new LinkedList<>();
                String resultStr = "";
                String projectPath = ServletUtil.getRequest().getContextPath();
                String avatar = "";
                avatar = userInfoFromSession.getAvatar();
                resultStr = getChildCommentHtml(comment, resultStr, projectPath, comment.getUserId());
                data.add(resultStr);
                json.setData(data);
            } else {
                json.setCode(0);
                json.setMsg("评论失败!");
            }
        }
        return json.toString();
    }

    @NotNull
    private String getChildCommentHtml(Comment comment, String resultStr, String projectPath, UserInfo userId) {
        resultStr += "<div class=\"comment-child\">";
        resultStr += "<a name=\"reply-1\"></a>";
        resultStr += "<img src=\"" + projectPath + "/file/getImg/" + comment.getUserId().getAvatar() + "\">";
        resultStr += "<div class=\"info\">";
        resultStr += "<span class=\"username\">" + comment.getUserId().getNickname() + "</span>\n" +
                " <span style=\"padding-right:0;margin-left:-5px;\">回复</span>\n" +
                "<span class=\"username\">" + comment.getRepliedUserId().getNickname() + "</span>\n" +
                " <span class=\"comments\">" + comment.getContent() + "</span>";
        resultStr += "</div>";
        resultStr += "<p class=\"info\">\n" +
                "<span class=\"comment-time\">" + DateUtils.dateToString(comment.getCreateTime()) + "</span>\n" +
                "<a href=\"javascript:;\" class=\"btn-reply\" " +
                "data-parentCommentId=\"" + comment.getId() + "\" " +
                "data-repliedUserNickname=\"" + userId.getNickname() + "\" " +
                "data-repliedUserId=\"" + comment.getUserId().getId() + "\">回复</a>\n" +
                "</p>";
        resultStr += "</div>";
        return resultStr;
    }

    private void transformAvatarPath(List<Comment> allComments) {
        String avatar = "";
        String avatar2 = "";
        for (Comment c :
                allComments) {
            avatar = c.getUserId().getAvatar();
            if (!avatar.startsWith("D:") && !avatar2.startsWith("/linux")) {
                avatar = "/linux" + avatar;
                c.getUserId().setAvatar(avatar);
            }

            for (Comment childComment :
                    c.getChildComments()) {
                avatar2 = childComment.getUserId().getAvatar();
                if (!avatar2.startsWith("D:") && !avatar2.startsWith("/linux")) {
                    avatar2 = "/linux" + avatar2;
                    childComment.getUserId().setAvatar(avatar2);
                }
            }
        }
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

    private String commentDataToJson(List<Comment> commentData) {
        ResponseUtil<Map> json = new ResponseUtil<>();
        if (null == commentData) {
            json.setCode(1);
            json.setMsg("获取评论信息失败");
        } else if (commentData.isEmpty()) {
            json.setCode(1);
            json.setMsg("用户评论为空");
        } else {
            json.setCode(0);
            json.setCount(commentsMapper.countComment());
            json.setMsg("获取评论信息成功");
            List<Map> jsonMap = new LinkedList<>();
            for (Comment comment : commentData) {
                Map<String, Object> temp = new HashMap<>();
                temp.put("id", comment.getId());
                temp.put("nickname", comment.getUserId().getNickname());
                temp.put("email", comment.getUserId().getEmail());
                temp.put("content", comment.getContent());
                temp.put("createTime", DateUtils.dateToString(comment.getCreateTime()));
                temp.put("blogId", comment.getBlogId());
                temp.put("replyName", comment.getRepliedUserId().getNickname());
                temp.put("parentCommentId", comment.getParentCommentId());
                jsonMap.add(temp);
            }
            json.setData(jsonMap);
        }
        return json.toString();
    }
}
