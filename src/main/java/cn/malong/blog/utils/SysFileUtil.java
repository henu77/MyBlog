package cn.malong.blog.utils;

import java.io.File;

/**
 * @author malong
 * @Date 2021-11-05 00:05:34
 */
public class SysFileUtil {
    /**
     * windows 系统文件上传路径
     */
    private static String WINDOWS_PATH = "D:\\home\\uploads\\MyBlog\\";

    /**
     * linux 系统文件上传路径
     */
    private static String LINUX_PATH = "/home/uploads/MyBlog/";

    /**
     * upload path 根据系统环境获取上传路径
     */
    public static String getUploadPath() {
        return '\\' == File.separatorChar ? WINDOWS_PATH : LINUX_PATH;
    }
}
