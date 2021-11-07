package cn.malong.blog.controller;

import cn.malong.blog.pojo.Type;
import cn.malong.blog.service.TypesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/add")
    public String typeAdd(@RequestBody Type type){
        return typesService.typeAdd(type);
    }

    @PutMapping("/update")
    public String typeUpdate(@RequestBody Type type){
        return typesService.typeUpdate(type);
    }

    @DeleteMapping("/delete")
    public String typeDelete(int id){
        return typesService.typeDelete(id);
    }
}
