package cn.malong.blog.controller;

import cn.malong.blog.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * @author malong
 * @Date 2021-11-01 10:53:42
 */
@RestController
@RequestMapping("/file")
public class FileController {


    @Autowired
    private FileService fileServiceImpl;

    @PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file) {
        return fileServiceImpl.upload(file);
    }

    @GetMapping("/getHeadImg")
    public void download() {
        fileServiceImpl.download();
    }
}
