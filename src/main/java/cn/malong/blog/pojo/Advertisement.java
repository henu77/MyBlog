package cn.malong.blog.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Advertisement {
    private int id;
    private String path;
    private String icon;
    private String title;
    private String des;
    private String miniDes;
    private String email;
    private boolean state;
    private UserInfo userId;
    private Date submitTime;
}
