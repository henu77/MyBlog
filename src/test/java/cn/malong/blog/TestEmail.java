package cn.malong.blog;

import cn.malong.blog.dao.TypesMapper;
import cn.malong.blog.pojo.Type;
import cn.malong.blog.utils.DateUtils;
import cn.malong.blog.utils.StaticVariable;
import com.sun.org.apache.bcel.internal.generic.DUP;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.List;

/**
 * @author Csy
 * @Classname TestTypesMapper
 * @date 2021-10-31 22:14
 * @Description TODO
 */

@SpringBootTest
@Transactional
public class TestEmail {
    @Autowired
    JavaMailSenderImpl mailSender;

    @Test
    public void contextLoads() {
        //邮件设置1：一个简单的邮件
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("通知-明天来狂神这听课");
        message.setText("今晚7:30开会");
        message.setTo("909229859@qq.com");
        message.setFrom("1289596706@qq.com");
        mailSender.send(message);
    }

    @Test
    public void contextLoads2() throws Exception {
        System.out.println("进入方法===》"+ DateUtils.dateToString(new Date()));
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
        messageHelper.setSubject("MyBlog注册验证码");
        String context = "<div>\n" +
                "\t<p>欢迎使用 MyBlog !</p>\n" +
                "  \t<p>您的验证码是：<span style=\"color: coral;font-size: 25px;font-weight: bold\">" + 45123 + "</span></p>\n" +
                "  \t<p>如果您有任何疑问，可以联系管理员" + StaticVariable.ADMIN_EMAIL + "</p>\n" +
                "  \t<p></p>\n" +
                "  \t<p></p>\n" +
                "  \t<p>(这是一封自动产生的email，请勿回复。)</p>\n" +
                "</div>";
        messageHelper.setText(context, true);
        messageHelper.setTo("1289596706@qq.com");
        messageHelper.setFrom(StaticVariable.ADMIN_EMAIL);
        System.out.println("开始发邮件===》"+ DateUtils.dateToString(new Date()));
        mailSender.send(mimeMessage);
        System.out.println("发送完毕===》"+ DateUtils.dateToString(new Date()));
    }
}
