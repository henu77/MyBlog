package cn.malong.blog.controller;

import cn.malong.blog.service.FileService;
import cn.malong.blog.utils.StaticVariable;
import cn.malong.blog.utils.SysFileUtil;
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

    @GetMapping("/getImg/{system}/home/uploads/MyBlog/{date}/{filename}")
    public void downloadImg(@PathVariable("system") String system,
                            @PathVariable("date") String date,
                            @PathVariable("filename") String filename) {
        String path = "/home/uploads/MyBlog/" + date + "/" + filename;
        if (!StaticVariable.OS_LINUX.equals(system)) {
            path = "/" + system + path;
        }
        fileServiceImpl.downloadImg(path);
    }

    @RequestMapping("/getCaptchaImg")
    public void getVerifyCodeImg() {
        fileServiceImpl.getVerifyCodeImg();
    }
}
