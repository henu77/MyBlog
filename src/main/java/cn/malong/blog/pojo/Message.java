package cn.malong.blog.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author marlone
 * @Date 2021/12/8 17:59
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    private int id;
    private UserInfo userId;
    private String content;
    private Date createTime;
}
