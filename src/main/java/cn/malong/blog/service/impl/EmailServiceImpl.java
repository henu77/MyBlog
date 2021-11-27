package cn.malong.blog.service.impl;

import cn.malong.blog.service.EmailService;
import cn.malong.blog.utils.ResponseUtil;
import cn.malong.blog.utils.StaticVariable;
import cn.malong.blog.utils.servlet.ServletUtil;
import com.alibaba.druid.sql.visitor.functions.Char;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author marlone
 * @Date 2021/11/22 15:51
 */
@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    JavaMailSenderImpl mailSender;

    @Override
    public String sentVerifyCode(String receiver) {
        ResponseUtil<String> json = new ResponseUtil<>();
        try {
            String verifyCode = getVerifyCode();
            HttpSession session = ServletUtil.getSession();
            session.setAttribute("registerEmailVerifyCode", verifyCode);
            sendEmail(receiver, verifyCode);
            //定时90s删除
            removeAttributeFromSession("registerEmailVerifyCode", 90);
            json.setCode(1);
            json.setMsg("验证码发送成功！");
        } catch (Exception e) {
            e.printStackTrace();
            json.setCode(0);
            json.setMsg("验证码发送失败！");
        } finally {
            return json.toString();
        }
    }

    @Override
    public void sendEmail(String receiver, String verifyCode) throws Exception {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
        messageHelper.setSubject("MyBlog注册验证码");
        String context = "<div>\n" +
                "\t<p>欢迎使用 MyBlog !</p>\n" +
                "  \t<p>您的验证码是：<span style=\"color: coral;font-size: 25px;font-weight: bold\">" + verifyCode + "</span></p>\n" +
                "  \t<p>如果您有任何疑问，可以联系管理员" + StaticVariable.ADMIN_EMAIL + "</p>\n" +
                "  \t<p></p>\n" +
                "  \t<p></p>\n" +
                "  \t<p>(这是一封自动产生的email，请勿回复。)</p>\n" +
                "</div>";
        messageHelper.setText(context, true);
        messageHelper.setTo(receiver);
        messageHelper.setFrom(StaticVariable.ADMIN_EMAIL);
        mailSender.send(mimeMessage);
    }


    String getVerifyCode() {
        String code = "";
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            code += random.nextInt(10);
        }
        return code;
    }

    /**
     * 定时删除session中的信息
     *
     * @param attrName
     */
    private void removeAttributeFromSession(final String attrName, int seconds) {
        HttpSession session = ServletUtil.getSession();
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                session.removeAttribute(attrName);
                timer.cancel();
            }
        }, seconds * 1000);
    }
}
