package cn.malong.blog.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author malong
 * @Date 2021-10-30 20:20:18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Blog {
    private int id;
    private String title;
    private String content;
    private String firstPicture;
    private String flag;
    private int views;
    private boolean appreciation;
    private boolean shareStatement;
    private boolean commentabled;
    private boolean published;
    private boolean recommend;
    private Date creatTime;
    private Date updateTime;
    private String description;
    private int typeId;
    private int userId;
    private String tagIds;
}
