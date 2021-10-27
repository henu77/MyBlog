package cn.malong.blog.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 马龙
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {
    private int id;
    private String nickname;
    private String username;
    private String password;
    private String email;
    private String avatar;
    private String role;
}
