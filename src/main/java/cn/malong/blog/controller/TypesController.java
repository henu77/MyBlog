package cn.malong.blog.controller;

import cn.malong.blog.service.TypesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Csy
 * @Classname TypesController
 * @date 2021-11-04 17:20
 * @Description TODO
 */
@RestController
@RequestMapping("/type")
public class TypesController {

    @Autowired
    private TypesService typesService;

    @RequestMapping("/dataLimit")
    public String getTypesByLimit(int page,int limit){
        return typesService.getTypesByLimit(page,limit);
    }
}
