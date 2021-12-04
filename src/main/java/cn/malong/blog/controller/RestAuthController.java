package cn.malong.blog.controller;

import cn.malong.blog.service.RestAuthService;
import com.alibaba.fastjson.JSONObject;
import com.xkcoding.http.config.HttpConfig;
import lombok.extern.slf4j.Slf4j;
import me.zhyd.oauth.config.AuthConfig;
import me.zhyd.oauth.enums.scope.*;
import me.zhyd.oauth.exception.AuthException;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.model.AuthUser;
import me.zhyd.oauth.request.*;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.utils.AuthScopeUtils;
import me.zhyd.oauth.utils.AuthStateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/oauth")
public class RestAuthController {

    @Autowired
    private RestAuthService restAuthServiceImpl;

    @RequestMapping("/render/{source}")
    @ResponseBody
    public void renderAuth(@PathVariable("source") String source, HttpServletResponse response) throws IOException {
        restAuthServiceImpl.renderAuth(source, response);
    }

    /**
     * oauth平台中配置的授权回调地址，以本项目为例，在创建github授权应用时的回调地址应为：http://127.0.0.1:8443/oauth/callback/github
     */
    @RequestMapping("/callback/{source}")
    public ModelAndView login(@PathVariable("source") String source, AuthCallback callback, HttpServletRequest request) {
        return restAuthServiceImpl.login(source, callback, request);
    }

    @PostMapping("/binding")
    public String binding(String username,
                          String password,
                          String source,
                          String uuid) {
        return restAuthServiceImpl.binding(username, password, source, uuid);
    }

    @PostMapping("/registerAndBinding")
    public String registerAndBinding(String username,
                                     String password,
                                     String email,
                                     String source,
                                     String uuid) {
        return restAuthServiceImpl.registerAndBinding(username, password, email, source, uuid);
    }
}