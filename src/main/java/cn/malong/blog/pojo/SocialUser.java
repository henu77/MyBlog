package cn.malong.blog.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author malong
 * @Date 2021-10-30 20:29:49
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SocialUser {
    private int id;
    private String uuid;
    private String source;
    private String accessToken;
}
