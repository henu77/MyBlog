package cn.malong.blog.service.impl;

import cn.malong.blog.pojo.UserInfo;
import cn.malong.blog.service.FileService;
import cn.malong.blog.utils.ResponseUtil;
import cn.malong.blog.utils.SequenceUtil;

import cn.malong.blog.utils.SysFileUtil;
import cn.malong.blog.utils.VerifyCode;
import cn.malong.blog.utils.servlet.ServletUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;

import java.util.ArrayList;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


/**
 * @author malong
 * @Date 2021-11-01 10:54:36
 */
@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private VerifyCode verifyCode;

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
            String parentPath = SysFileUtil.getUploadPath() + fileDir;
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

    @Override
    public void downloadImg(String path) {
        try {
            java.io.File files = new java.io.File(path);
            if (files.exists()) {
                FileCopyUtils.copy(new FileInputStream(path), ServletUtil.getResponse().getOutputStream());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getVerifyCodeImg() {
        BufferedImage image = verifyCode.getImage();
        ServletUtil.getSession().setAttribute("login_VerifyCode", verifyCode.getVerifyCode());
        removeAttributeFromSession("login_VerifyCode", 60);
        try {
            verifyCode.outPut(image, ServletUtil.getResponse().getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void removeAttributeFromSession(final String attrName, int seconds) {
        HttpSession session = ServletUtil.getSession();
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                session.removeAttribute(attrName);
                timer.cancel();
            }
        }, seconds * 1000);
    }
}
