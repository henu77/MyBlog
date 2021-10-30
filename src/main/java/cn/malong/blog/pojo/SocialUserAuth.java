package cn.malong.blog.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author malong
 * @Date 2021-10-30 20:29:31
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SocialUserAuth {
    private int id;
    private int user_id;
    private int socialUserId;
}
