package cn.malong.blog.controller;

import cn.malong.blog.pojo.Advertisement;
import cn.malong.blog.service.AdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ad")
public class AdController {
    @Autowired
    private AdService adServiceImpl;

    @PutMapping("/submit")
    public String submit(@RequestBody Advertisement advertisement) {
        return adServiceImpl.submit(advertisement);
    }
    @RequestMapping("/dataLimit")
    public String dataLimit(int page, int limit) {
        return adServiceImpl.dataLimit(page, limit);
    }
}
