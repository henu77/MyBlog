package cn.malong.blog;

import cn.malong.blog.dao.TypesMapper;
import cn.malong.blog.pojo.Type;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

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
}
