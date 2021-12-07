package cn.malong.blog.controller;

import cn.malong.blog.pojo.Advertisement;
import cn.malong.blog.service.AdService;
import cn.malong.blog.utils.servlet.ServletUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    @DeleteMapping("/delete")
    public String deleteAd(int id) {
        return adServiceImpl.deleteById(id);
    }

    @DeleteMapping("/batchDelete")
    public String batchDeleteAd(int[] ids) {
        return adServiceImpl.aDBatchDelete(ids);
    }

    @PutMapping("/pass/{id}")
    public String pass(@PathVariable("id") int id) {
        return adServiceImpl.pass(id);
    }

    @PutMapping("/refuse/{id}")
    public String refuse(@PathVariable("id") int id) {
        return adServiceImpl.refuse(id);
    }


}
