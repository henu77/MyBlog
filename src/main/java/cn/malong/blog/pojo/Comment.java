package cn.malong.blog.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @author malong
 * @Date 2021-10-30 20:15:21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    private int id;
    private UserInfo userId;
    private String content;
    private Date createTime;
    private int blogId;
    private UserInfo repliedUserId;
    private int parentCommentId;
    private List<Comment> childComments;
}
