package cn.malong.blog.controller;

import cn.malong.blog.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author marlone
 * @Date 2021/12/8 17:45
 */
@RestController
@RequestMapping("/message")
public class MessageController {
    @Autowired
    private MessageService messageServiceImpl;

    @PostMapping("/leaveAMessage")
    public String leaveAMessage(String content) {
        return messageServiceImpl.leaveAMessage(content);
    }
    @GetMapping("/more")
    public String more(int page) {
        return messageServiceImpl.more(page);
    }
}
