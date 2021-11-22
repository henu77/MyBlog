package cn.malong.blog.controller;

import cn.malong.blog.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @Author malong
 * @Date 2021/11/22 15:29
 */
@Controller
public class EmailController {

    @Autowired
    private EmailService emailServiceImpl;

    @PostMapping("/sendVerifyCode")
    public String sendVerifyCode(String receiver) {
        return emailServiceImpl.sentVerifyCode(receiver);
    }
}
