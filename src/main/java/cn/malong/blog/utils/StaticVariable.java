package cn.malong.blog.utils;

import java.util.Random;

/**
 * @author malong
 * @Date 2021-10-29 22:39:28
 */
public class StaticVariable {
    public final static String ROLE_ADMIN = "admin";
    public final static String ROLE_ROOT = "root";
    public final static String ROLE_USER = "user";
    public final static String SPACE = " ";
    public final static int FLOW_ARTICLE_PAGE_SIZE = 3;
    public final static int FLOW_MESSAGE_PAGE_SIZE = 6;
    public final static String OS_LINUX = "linux";
    public final static String ADMIN_EMAIL = "1289596706@qq.com";
    public final static String BAIDU_AK = "bCe88I0AvVB3DHhFKYplHOmMwKSbaYKE";
    public static final int OPERATE_PASS = 2000;
    public static final int OPERATE_REFUSE = 5000;
    public static final String AD_STATE_PASS = "审核通过";
    public static final String AD_STATE_REFUSE = "审核未通过";
    public static final String AD_STATE_UNDEFINED = "暂未审核";
    /**
     * 前缀匹配省份 如果匹配不到返回null
     *
     * @param name
     * @return
     */
    public static String getProvinceName(String name) {
        String result = "";
        if (name.startsWith("河北")) {
            result = "河北";
        } else if (name.startsWith("山西")) {
            result = "山西";
        } else if (name.startsWith("北京")) {
            result = "北京";
        } else if (name.startsWith("上海")) {
            result = "上海";
        } else if (name.startsWith("天津")) {
            result = "天津";
        } else if (name.startsWith("重庆")) {
            result = "重庆";
        } else if (name.startsWith("辽宁")) {
            result = "辽宁";
        } else if (name.startsWith("吉林")) {
            result = "吉林";
        } else if (name.startsWith("黑龙江")) {
            result = "黑龙江";
        } else if (name.startsWith("江苏")) {
            result = "江苏";
        } else if (name.startsWith("浙江")) {
            result = "浙江";
        } else if (name.startsWith("安徽")) {
            result = "安徽";
        } else if (name.startsWith("福建")) {
            result = "福建";
        } else if (name.startsWith("江西")) {
            result = "江西";
        } else if (name.startsWith("山东")) {
            result = "山东";
        } else if (name.startsWith("台湾")) {
            result = "台湾";
        } else if (name.startsWith("河南")) {
            result = "河南";
        } else if (name.startsWith("湖北")) {
            result = "湖北";
        } else if (name.startsWith("湖南")) {
            result = "湖南";
        } else if (name.startsWith("广东")) {
            result = "广东";
        } else if (name.startsWith("海南")) {
            result = "海南";
        } else if (name.startsWith("四川")) {
            result = "四川";
        } else if (name.startsWith("贵州")) {
            result = "贵州";
        } else if (name.startsWith("云南")) {
            result = "云南";
        } else if (name.startsWith("陕西")) {
            result = "陕西";
        } else if (name.startsWith("甘肃")) {
            result = "甘肃";
        } else if (name.startsWith("青海")) {
            result = "青海";
        } else if (name.startsWith("内蒙古")) {
            result = "内蒙古";
        } else if (name.startsWith("宁夏")) {
            result = "宁夏";
        } else if (name.startsWith("新疆")) {
            result = "新疆";
        } else if (name.startsWith("西藏")) {
            result = "西藏";
        } else if (name.startsWith("广西")) {
            result = "广西";
        } else if (name.startsWith("香港")) {
            result = "香港";
        } else if (name.startsWith("澳门")) {
            result = "澳门";
        } else {
            result = null;
        }
        return result;
    }

    public static String pathTransform(String oldPath) {
        if (!oldPath.startsWith("D:") && !oldPath.startsWith("/linux")) {
            oldPath = "/linux" + oldPath;
        }
        return oldPath;
    }

    public static String getDefaultIconPath() {
        Random random = new Random();
        return SysFileUtil.getUploadPath() + "/defaultIcon" + "/defaultIcon" + (random.nextInt(5) + 1) + ".png";
    }
}
