package cn.malong.blog.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author malong
 * @Date 2021-10-30 20:15:21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    private int id;
    private String nickname;
    private String email;
    private String content;
    private String avatar;
    private String createTime;
    private boolean adminComment;
    private int blogId;
    private String replyName;
    private String parentCommentId;
    private String topCommentId;
}
