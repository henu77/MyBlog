package cn.malong.blog.service;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * @author malong
 * @Date 2021-11-01 10:54:05
 */
public interface FileService {
    /**
     * 上传文件
     *
     * @param file
     * @return
     */
    String upload(MultipartFile file);

    /**
     * 下载文件
     */
    void download();

    void downloadImg(String path);
}
