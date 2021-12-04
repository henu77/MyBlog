package cn.malong.blog.service;

import cn.malong.blog.pojo.SocialUser;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.request.AuthRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author marlone
 * @Date 2021/12/3 18:22
 */
public interface RestAuthService {
    void renderAuth(String source, HttpServletResponse response) throws IOException;
    ModelAndView login( String source, AuthCallback callback, HttpServletRequest request);
    AuthRequest getAuthRequest(String source);
    void localLogin(SocialUser socialUser);

    String binding(String username, String password, String source, String uuid);

    String registerAndBinding(String username, String password, String email, String source, String uuid);
}
