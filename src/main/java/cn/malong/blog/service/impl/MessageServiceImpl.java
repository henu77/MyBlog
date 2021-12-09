package cn.malong.blog.service.impl;

import cn.malong.blog.dao.MessageMapper;
import cn.malong.blog.pojo.Message;
import cn.malong.blog.pojo.UserInfo;
import cn.malong.blog.service.MessageService;
import cn.malong.blog.utils.DateUtils;
import cn.malong.blog.utils.ResponseUtil;
import cn.malong.blog.utils.StaticVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author marlone
 * @Date 2021/12/8 17:49
 */
@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageMapper messageMapper;

    @Override
    public String leaveAMessage(String content) {
        ResponseUtil<String> json = new ResponseUtil<>();
        UserInfo userInfoFromSession = UserServiceImpl.getUserInfoFromSession();
        if (null == userInfoFromSession) {
            json.setCode(0);
            json.setMsg("请先登录！");
            return json.toString();
        }
        Message message = new Message();
        message.setContent(content);
        message.setUserId(userInfoFromSession);
        Date date = new Date();
        message.setCreateTime(date);
        int result = messageMapper.addAMessage(message);
        if (result > 0) {
            json.setCode(1);
            json.setMsg("留言成功！");
            List<String> data = new ArrayList<>();
            data.add("<li class=\"zoomIn article\">\n" +
                    "<div class=\"comment-parent\">\n" +
                    "   <a name=\"remark-1\"></a>\n" +
                    "   <img src=\"/file/getImg/" + StaticVariable.pathTransform(userInfoFromSession.getAvatar()) + "\"\n" +
                    "       alt=\"" + userInfoFromSession.getNickname() + "\"/>\n" +
                    "   <div class=\"info\">\n" +
                    "       <span class=\"username\">" + userInfoFromSession.getNickname() + "</span>\n" +
                    "   </div>\n" +
                    "   <div class=\"comment-content\">\n" +
                    "       " + content + "\n" +
                    "   </div>\n" +
                    "   <p class=\"info info-footer\">\n" +
                    "       <span class=\"comment-time\">" + DateUtils.dateToString(date) + "</span>\n" +
                    "   </p>\n" +
                    "</div>\n" +
                    "</li>");
            json.setData(data);
        } else {
            json.setCode(0);
            json.setMsg("留言失败！请联系管理员处理！");
        }
        return json.toString();
    }

    @Override
    public String more(int page) {
        ResponseUtil<String> json = new ResponseUtil<>();
        int limit = StaticVariable.FLOW_ARTICLE_PAGE_SIZE;
        int startIndex = (page - 1) * limit;
        List<Message> messageByPage = messageMapper.queryMessageByPage(startIndex, limit);
        if (null != messageByPage && messageByPage.size() > 0) {
            json.setCode(1);
            json.setMsg("查询成功");
            List<String> data = new ArrayList<>();
            for (Message item :
                    messageByPage) {
                data.add("<li class=\"zoomIn article\">\n" +
                        "<div class=\"comment-parent\">\n" +
                        "   <a name=\"remark-1\"></a>\n" +
                        "   <img src=\"/file/getImg/" + StaticVariable.pathTransform(item.getUserId().getAvatar()) + "\"\n" +
                        "       alt=\"" + item.getUserId().getNickname() + "\"/>\n" +
                        "   <div class=\"info\">\n" +
                        "       <span class=\"username\">" + item.getUserId().getNickname() + "</span>\n" +
                        "   </div>\n" +
                        "   <div class=\"comment-content\">\n" +
                        "       " + item.getContent() + "\n" +
                        "   </div>\n" +
                        "   <p class=\"info info-footer\">\n" +
                        "       <span class=\"comment-time\">" + DateUtils.dateToString(item.getCreateTime()) + "</span>\n" +
                        "   </p>\n" +
                        "</div>\n" +
                        "</li>");
            }
            json.setData(data);
        } else {
            json.setCode(0);
            json.setMsg("查询失败,请求接口错误");
        }
        int countAllMessages = messageMapper.countAllMessages();
        json.setPages((int) Math.ceil(1.0 * countAllMessages / limit));
        return json.toString();
    }
}
