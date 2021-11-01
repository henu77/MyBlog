package cn.malong.blog.controller;

import cn.malong.blog.service.TagsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Csy
 * @Classname TagsController
 * @date 2021-10-31 22:56
 * @Description TODO
 */
@RestController
@RequestMapping("/tag")
public class TagsController {

    private final TagsService tagsService;

    @Autowired
    public TagsController(TagsService tagsService) {
        this.tagsService = tagsService;
    }

    @RequestMapping("/dataLimit")
    public String getTagsByLimit(int page,int limit){
        return tagsService.getTagsByLimit(page,limit);
    }
}
