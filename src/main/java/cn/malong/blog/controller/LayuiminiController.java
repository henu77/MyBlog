package cn.malong.blog.controller;

import cn.malong.blog.utils.ReadJsonFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * @author malong
 */
@RestController
public class LayuiminiController {

    @Autowired
    private ReadJsonFile readJsonFile;

    @RequestMapping("/admin/api/init.json")
    public String init() throws FileNotFoundException {
        String path =  ResourceUtils.getFile("classpath:templates/admin/api/init.json").getPath();
        String json = readJsonFile.readFile(path);
        return json;
    }
    @RequestMapping("/admin/api/clear.json")
    public String clear() throws FileNotFoundException {
        String path =  ResourceUtils.getFile("classpath:templates/admin/api/clear.json").getPath();
        String json = readJsonFile.readFile(path);
        return json;
    }
}
