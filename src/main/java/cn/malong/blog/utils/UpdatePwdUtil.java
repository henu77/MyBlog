package cn.malong.blog.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author malong
 * @Date 2021-11-05 22:38:46
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePwdUtil {
    private String oldPassword;
    private String newPassword;
    private String confirmPassword;

    public boolean isSame() {
        return newPassword.equals(confirmPassword);
    }

    public String getOldPassword() {
        return MD5Util.string2MD5(oldPassword);
    }

    public String getNewPassword() {
        return MD5Util.string2MD5(newPassword);
    }

    public String getConfirmPassword() {
        return MD5Util.string2MD5(confirmPassword);
    }
}
