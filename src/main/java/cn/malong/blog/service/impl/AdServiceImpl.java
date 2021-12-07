package cn.malong.blog.service.impl;

import cn.malong.blog.dao.AdMapper;
import cn.malong.blog.pojo.Advertisement;
import cn.malong.blog.pojo.Comment;
import cn.malong.blog.pojo.UserInfo;
import cn.malong.blog.service.AdService;
import cn.malong.blog.utils.DateUtils;
import cn.malong.blog.utils.ResponseUtil;
import cn.malong.blog.utils.StaticVariable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.*;

@Slf4j
@Service
public class AdServiceImpl implements AdService {

    @Autowired
    private AdMapper adMapper;
    @Autowired
    JavaMailSenderImpl mailSender;

    @Override
    public String submit(Advertisement advertisement) {
        advertisement.setSubmitTime(new Date());
        ResponseUtil<String> json = new ResponseUtil<>();
        UserInfo userInfoFromSession = UserServiceImpl.getUserInfoFromSession();
        if (null == userInfoFromSession) {
            //用户未登录
            json.setCode(0);
            json.setMsg("请先登录！");
            return json.toString();
        }
        advertisement.setUserId(userInfoFromSession);
        advertisement.setState(StaticVariable.AD_STATE_UNDEFINED);
        int result = adMapper.insertAnAd(advertisement);
        if (result > 0) {
            //成功
            json.setCode(1);
            try {
                sendReminderEmail(userInfoFromSession, new Date());
                json.setMsg("提交成功");
            } catch (MessagingException e) {
                e.printStackTrace();
                json.setMsg("提交成功，但发送提醒邮件失败！");
            }
        } else {
            //失败
            json.setCode(0);
            json.setMsg("提交失败");
        }
        return json.toString();
    }

    @Override
    public String dataLimit(int page, int limit) {
        int startIndex = (page - 1) * limit;
        List<Advertisement> advertisementDataByLimit = adMapper.getAdDataByLimit(startIndex, limit);
        ResponseUtil<Map<String, Object>> json = new ResponseUtil<>();
        if (null == advertisementDataByLimit) {
            json.setCode(1);
            json.setMsg("获取链接信息失败");
        } else if (advertisementDataByLimit.isEmpty()) {
            json.setCode(1);
            json.setMsg("链接为空");
        } else {
            json.setCode(0);
            json.setCount(adMapper.allAdCount());
            json.setMsg("获取链接信息成功");
            List<Map<String, Object>> jsonMap = new LinkedList<>();
            for (Advertisement advertisement : advertisementDataByLimit) {
                Map<String, Object> temp = new HashMap<>();
                temp.put("id", advertisement.getId());
                temp.put("nickname", advertisement.getUserId().getNickname());
                temp.put("userId", advertisement.getUserId().getId());
                temp.put("email", advertisement.getEmail());
                temp.put("linkPath", advertisement.getPath());
                temp.put("title", advertisement.getTitle());
                temp.put("icon", advertisement.getIcon());
                temp.put("miniDes", advertisement.getMiniDes());
                temp.put("des", advertisement.getDes());
                if (advertisement.getState().equals(StaticVariable.AD_STATE_PASS)) {
                    temp.put("nowState", "<span style='color: #5FB878'>" + advertisement.getState() + "</span>");
                } else if (advertisement.getState().equals(StaticVariable.AD_STATE_REFUSE)) {
                    temp.put("nowState", "<span style='color: #FF5722'>" + advertisement.getState() + "</span>");
                } else {
                    temp.put("nowState", "<span style='color: #393D49'>" + advertisement.getState() + "</span>");
                }
                temp.put("submitTime", DateUtils.dateToString(advertisement.getSubmitTime()));
                jsonMap.add(temp);
            }
            json.setData(jsonMap);
        }
        return json.toString();
    }

    @Override
    public String deleteById(int id) {
        ResponseUtil<String> json = new ResponseUtil<>();
        if (!UserServiceImpl.isHavingRootAuthority()) {
            json.setCode(0);
            json.setMsg("您的权限太低，无法删除");
            return json.toString();
        }
        int ret = adMapper.deleteComment(id);
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
    public String aDBatchDelete(int[] ids) {
        ResponseUtil<String> json = new ResponseUtil<>();
        if (!UserServiceImpl.isHavingRootAuthority()) {
            json.setCode(0);
            json.setMsg("您的权限太低，无法删除");
            return json.toString();
        }
        int ret = adMapper.deleteCommentsByBatch(ids);
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
    public String pass(int id) {
        return refuseOrPass(id, StaticVariable.OPERATE_PASS);
    }

    @Override
    public String refuse(int id) {
        return refuseOrPass(id, StaticVariable.OPERATE_REFUSE);
    }

    private String refuseOrPass(int id, int operate) {
        ResponseUtil<String> json = new ResponseUtil<>();
        if (!UserServiceImpl.isHavingRootAuthority()) {
            json.setCode(0);
            json.setMsg("您的权限太低，无法删除");
            return json.toString();
        }
        String msg = "";
        String state = "";
        if (operate == StaticVariable.OPERATE_PASS) {
            msg = "审核";
            state = StaticVariable.AD_STATE_PASS;
        } else {
            msg = "拒绝通过";
            state = StaticVariable.AD_STATE_REFUSE;
        }
        log.info("操作=====>" + operate);
        log.info("state=====>" + state);
        Advertisement advertisementById = adMapper.queryAdById(id);
        if (null != advertisementById) {
            int result = adMapper.updateState(id, state);
            if (result > 0) {
                json.setCode(1);
                json.setMsg(msg + "成功！");
                try {
                    sendCheckFinishEmail(advertisementById.getUserId(), advertisementById.getSubmitTime(), operate);
                } catch (MessagingException e) {
                    e.printStackTrace();
                    json.setMsg(msg + "成功，但通知邮件未发出");
                }
            } else {
                json.setCode(0);
                json.setMsg(msg + "失败！");
            }
        } else {
            json.setCode(0);
            json.setMsg("您要" + msg.substring(0, 2) + "的申请不存在！");
        }
        return json.toString();
    }

    @Async
    void sendReminderEmail(UserInfo userInfo, Date date) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
        messageHelper.setSubject("有新用户提交友链申请了");
        String context = "<div>\n" +
                "\t<h1 style='color: #1E9FFF'>温馨提示：</h1>\n" +
                "  \t<p></p>\n" +
                "  \t<p></p>\n" +
                "  \t<h3>用户：" + userInfo.getUsername() + " (昵称：" + userInfo.getNickname()
                + ")，于" + DateUtils.dateToString(date) + "</h3>"
                + "<h3>提交了新的友链申请，请您及时审核！</h3>\n" +
                "  \t<p></p>\n" +
                "  \t<p></p>\n" +
                "  \t<p>(这是一封自动产生的email，请勿回复。)</p>\n" +
                "</div>";
        messageHelper.setText(context, true);
        messageHelper.setTo(StaticVariable.ADMIN_EMAIL);
        messageHelper.setFrom(StaticVariable.ADMIN_EMAIL);
        mailSender.send(mimeMessage);
    }

    //经常宕机  不合法规    红标报毒  原创优先  技术优先 Review completed email
    @Async
    void sendCheckFinishEmail(UserInfo userInfo, Date date, int operate) throws MessagingException {
        String result = operate == StaticVariable.OPERATE_PASS ?
                "<span style='color: #5FB878;font-size: 17px;'>通过</span>"
                : "<span style='color: #FF5722;font-size: 17px;'>未通过</span>";
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
        messageHelper.setSubject("友链审核结果");
        String context = "<div>\n" +
                "\t<h1 style='color: #1E9FFF'>温馨提示：</h1>\n" +
                "  \t<p></p>\n" +
                "  \t<p></p>\n" +
                "  \t<h3>尊敬的用户：" + userInfo.getUsername() + " (昵称：" + userInfo.getNickname()
                + ")，您于" + DateUtils.dateToString(date) + "</h3>"
                + "<h3>提交的友链申请的审核结果为：" + result + "</h3>\n" +
                "  \t<p></p>\n" +

                "  \t<p></p>\n" +
                "  \t<p>(这是一封自动产生的email，请勿回复。)</p>\n" +
                "</div>";
        messageHelper.setText(context, true);
        messageHelper.setTo(userInfo.getEmail());
        messageHelper.setFrom(StaticVariable.ADMIN_EMAIL);
        mailSender.send(mimeMessage);
    }
}
