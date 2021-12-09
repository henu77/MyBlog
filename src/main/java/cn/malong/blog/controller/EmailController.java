package cn.malong.blog.controller;

import cn.malong.blog.pojo.UserInfo;
import cn.malong.blog.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @Author malong
 * @Date 2021/11/22 15:29
 */
@RestController
@RequestMapping("/email")
public class EmailController {

    @Autowired
    private EmailService emailServiceImpl;

    @PostMapping("/sendVerifyCode")
    public String sendVerifyCode(@RequestParam("receiver") String receiver) {
        return emailServiceImpl.sentVerifyCode(receiver);
    }

    @PostMapping("/sendPwdVerifyCode")
    public String sendPwdVerifyCode(@RequestParam("receiver") String receiver,
                                    @RequestParam("username") String username) {
        return emailServiceImpl.sendPwdVerifyCode(receiver, username);
    }
}
