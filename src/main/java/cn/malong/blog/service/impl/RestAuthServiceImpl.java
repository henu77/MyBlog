package cn.malong.blog.service.impl;

import cn.malong.blog.dao.SocialUserAuthMapper;
import cn.malong.blog.dao.SocialUserMapper;
import cn.malong.blog.dao.UserInfoMapper;
import cn.malong.blog.pojo.SocialUser;
import cn.malong.blog.pojo.SocialUserAuth;
import cn.malong.blog.pojo.UserInfo;
import cn.malong.blog.service.RestAuthService;
import cn.malong.blog.utils.MD5Util;
import cn.malong.blog.utils.ResponseUtil;
import cn.malong.blog.utils.StaticVariable;
import cn.malong.blog.utils.servlet.ServletUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import me.zhyd.oauth.config.AuthConfig;
import me.zhyd.oauth.exception.AuthException;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.model.AuthUser;
import me.zhyd.oauth.request.AuthAliyunRequest;
import me.zhyd.oauth.request.AuthGiteeRequest;
import me.zhyd.oauth.request.AuthRequest;
import me.zhyd.oauth.utils.AuthStateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author marlone
 * @Date 2021/12/3 18:22
 */
@Slf4j
@Service
public class RestAuthServiceImpl implements RestAuthService {

    @Autowired
    private SocialUserMapper socialUserMapper;
    @Autowired
    private SocialUserAuthMapper socialUserAuthMapper;

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public void renderAuth(String source, HttpServletResponse response) throws IOException {
        log.info("进入render：" + source);
        AuthRequest authRequest = getAuthRequest(source);
        String authorizeUrl = authRequest.authorize(AuthStateUtils.createState());
        log.info(authorizeUrl);
        response.sendRedirect(authorizeUrl);
    }

    @Override
    public ModelAndView login(String source, AuthCallback callback, HttpServletRequest request) {
        log.info("进入callback：" + source + " callback params：" + JSONObject.toJSONString(callback));
        AuthRequest authRequest = getAuthRequest(source);
        AuthResponse<AuthUser> response = authRequest.login(callback);
        log.info(JSONObject.toJSONString(response));

        //第三方认证成功
        if (response.ok()) {
            SocialUser socialUser =
                    socialUserMapper.querySocialUserByUUidAndSource(response.getData().getUuid(), source);
            if (null != socialUser) {
                localLogin(socialUser);
                return new ModelAndView("redirect:/user/toArticle");
            } else {
                ModelAndView modelAndView = new ModelAndView("/user/auth-register");
                modelAndView.addObject("source", response.getData().getSource());
                modelAndView.addObject("socialUsername", response.getData().getUsername());
                modelAndView.addObject("uuid", response.getData().getUuid());
                modelAndView.addObject("accessToken", response.getData().getToken().getAccessToken());
                return modelAndView;
            }
        } else {
            //第三方认证失败，转发回登录界面
            Map<String, Object> map = new HashMap<>(1);
            map.put("errorMsg", request.getParameter("error_description") + "\n" + response.getMsg());
            log.info(map.toString());
            return new ModelAndView("user/login", map);
        }
    }

    @Override
    public AuthRequest getAuthRequest(String source) {
        AuthRequest authRequest = null;
        switch (source.toLowerCase()) {
            case "aliyun":
                authRequest = new AuthAliyunRequest(AuthConfig.builder()
                        .clientId("4710766938518379518")
                        .clientSecret("f1pm443iIP6ZLdUbQA1qLN9ZE98rRsZbGbXseZwxaDCmwHGkm4ETFsw6CsdXULa0")
                        .redirectUri("http://localhost:8080/oauth/callback/aliyun")
                        .build());
                break;
            case "gitee":
                authRequest = new AuthGiteeRequest(AuthConfig.builder()
                        .clientId("76c650ce3901f75ce005dee187b6534a316c7872c3b6fbb852b9b038f3334ff9")
                        .clientSecret("78055b31c7317c49590da85c319b48556fc114be0d3e46d7ab4eebb3eac56ab1")
                        .redirectUri("http://localhost:8080/oauth/callback/gitee")
                        .build());
                break;
            case "oschina":
                authRequest = new AuthGiteeRequest(AuthConfig.builder()
                        .clientId("JpqCuNeD76MVKSxEiDP1")
                        .clientSecret("s3rd0UNojS7XSyCygWV5QGE6FfyKZpQr")
                        .redirectUri("http://localhost:8080/oauth/callback/oschina")
                        .build());
                break;
            default:
                break;
        }
        if (null == authRequest) {
            throw new AuthException("未获取到有效的Auth配置");
        }
        return authRequest;
    }

    @Override
    public void localLogin(SocialUser socialUser) {
        SocialUserAuth socialUserAuth
                = socialUserAuthMapper.queryBySocialUserId(socialUser.getId());
        ServletUtil.getSession().setAttribute("userInfo", socialUserAuth.getUserId());
    }

    /**
     * 首先检查账号密码是否正确
     * 检查7
     *
     * @param username
     * @param password
     * @param source
     * @param uuid
     * @return
     */
    @Override
    public String binding(String username, String password, String source, String uuid) {
        ResponseUtil<String> json = new ResponseUtil<>();
        UserInfo userInfoByUsername = userInfoMapper.getUserInfoByUsername(username);
        if (null != userInfoByUsername) {
            if (!MD5Util.passwordIsTrue(password, userInfoByUsername.getPassword())) {
                //密码不正确
                json.setCode(0);
                json.setMsg("密码错误！");
                return json.toString();
            }
            userInfoBindSocial(source, uuid, json, userInfoByUsername);
        } else {
            json.setCode(0);
            json.setMsg("查询用户信息失败！");
        }
        return json.toString();
    }

    @Override
    public String registerAndBinding(String username, String password, String email, String source, String uuid) {
        ResponseUtil<String> json = new ResponseUtil<>();
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername(username);
        userInfo.setPassword(MD5Util.string2MD5(password));
        userInfo.setAvatar(StaticVariable.getDefaultIconPath());
        userInfo.setNickname(username);
        userInfo.setEmail(email);
        userInfo.setRole("user");
        int result = userInfoMapper.userAdd(userInfo);
        if (result > 0) {
            userInfoBindSocial(source, uuid, json, userInfo);
        } else {
            json.setCode(0);
            json.setMsg("注册MyBlog用户失败");
        }
        return json.toString();
    }

    private void userInfoBindSocial(String source, String uuid, ResponseUtil<String> json, UserInfo userInfo) {
        SocialUser socialUser = new SocialUser();
        socialUser.setUuid(uuid);
        socialUser.setSource(source);
        int result1 = socialUserMapper.addSocialUser(socialUser);
        if (result1 > 0) {
            int result2 = socialUserAuthMapper.addSocialUserAuth(userInfo.getId(), socialUser.getId());
            if (result2 > 0) {
                json.setCode(1);
                json.setMsg("绑定成功！");
                localLogin(socialUser);
            } else {
                json.setCode(0);
                json.setMsg("添加验证信息失败！");
            }
        } else {
            json.setCode(0);
            json.setMsg("添加第三方信息失败！");
        }
    }
}
