package cn.malong.blog.pojo;

import cn.malong.blog.utils.DateUtils;
import lombok.Data;


import java.util.Date;

/**
 * @author marlone
 * @Date 2021/11/28 15:44
 */
@Data
public class TrafficStatics {
    private Date date;
    private int views;
    private String dateStr;

    public TrafficStatics() {
        this.date = new Date();
    }

    public TrafficStatics(int views) {
        this.date = new Date();
        this.views = views;
    }

    public String getDate() {
        return DateUtils.getYMD(date);
    }

}
