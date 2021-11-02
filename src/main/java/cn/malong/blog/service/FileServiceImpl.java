package cn.malong.blog.service;

import cn.malong.blog.pojo.UserInfo;
import cn.malong.blog.utils.ResponseUtil;
import cn.malong.blog.utils.SequenceUtil;

import cn.malong.blog.utils.servlet.ServletUtil;
import org.springframework.stereotype.Service;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;

import java.io.FileInputStream;
import java.time.LocalDate;

import java.util.ArrayList;

import java.util.List;


/**
 * @author malong
 * @Date 2021-11-01 10:54:36
 */
@Service
public class FileServiceImpl implements FileService {

    /**
     * windows 系统文件上传路径
     */
    private String windowsPath = "D:\\home\\uploads\\MyBlog\\";

    /**
     * linux 系统文件上传路径
     */
    private String linuxPath = "/home/uploads/MyBlog/";

    /**
     * upload path 根据系统环境获取上传路径
     */
    public String getUploadPath() {
        return '\\' == File.separatorChar ? this.windowsPath : this.linuxPath;
    }


    @Override
    public String upload(MultipartFile file) {
        ResponseUtil<String> json = new ResponseUtil<>();
        try {
            String fileId = SequenceUtil.makeStringId();
            String name = file.getOriginalFilename();
            assert name != null;
            String suffixName = name.substring(name.lastIndexOf("."));
            String fileName = fileId + suffixName;
            String fileDir = LocalDate.now().toString();
            String parentPath = getUploadPath() + fileDir;
            File filepath = new File(parentPath, fileName);
            if (!filepath.getParentFile().exists()) {
                filepath.getParentFile().mkdirs();
            }
            file.transferTo(filepath);
            json.setCode(1);
            json.setMsg("上传成功！");
            List<String> list = new ArrayList<>();
            list.add(filepath.getPath());
            json.setData(list);
        } catch (Exception e) {
            json.setCode(0);
            json.setMsg("上传失败！");
            System.out.println("failed to upload file.detail message:" + e.getMessage());
        }
        return json.toString();
    }

    @Override
    public void download() {
        UserInfo userInfo = (UserInfo) ServletUtil.getSession().getAttribute("userInfo");
        String path = userInfo.getAvatar();
        try {
            java.io.File files = new java.io.File(path);
            if (files.exists()) {
                FileCopyUtils.copy(new FileInputStream(path), ServletUtil.getResponse().getOutputStream());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
