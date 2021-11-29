package cn.malong.blog.aspect;

import cn.malong.blog.dao.ProvinceMapper;
import cn.malong.blog.dao.UserInfoMapper;
import cn.malong.blog.pojo.UserInfo;
import cn.malong.blog.utils.ResponseUtil;
import cn.malong.blog.utils.StaticVariable;
import cn.malong.blog.utils.servlet.ServletUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * @author marlone
 * @Date 2021/11/24 13:34
 */
@Aspect
@Component
@Slf4j
public class LoginAspect {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ProvinceMapper provinceMapper;

    @Around("execution(* cn.malong.blog.controller.LoginController.login(..))")
    public String around(ProceedingJoinPoint pj) throws Throwable {
        Object[] args = pj.getArgs();
        String result = (String) pj.proceed(args);
        ResponseUtil<String> json = JSONObject.parseObject(result, ResponseUtil.class);
        if (json.getCode() == 1) {
            //登录成功
            //统计所在省份
            addProvinceViewCounts();

            String username = (String) args[0];
            UserInfo userInfoByUsername = userInfoMapper.getUserInfoByUsername_recent(username);
            String avatar = userInfoByUsername.getAvatar();
            if (!avatar.startsWith("D:")) {
                userInfoByUsername.setAvatar("/linux" + avatar);
            }
            ServletContext servletContext = ServletUtil.getRequest().getServletContext();
            Set<UserInfo> recentViewUser = (LinkedHashSet<UserInfo>) servletContext.getAttribute("recentViewUser");
            if (null == recentViewUser) {
                recentViewUser = new LinkedHashSet<>();
            }
            recentViewUser.add(userInfoByUsername);
            if (recentViewUser.size() > 12) {
                recentViewUser.remove(0);
            }
            servletContext.setAttribute("recentViewUser", recentViewUser);
        }
        return result;
    }

    @Async
    void addProvinceViewCounts() {
        provinceMapper.addCount(getIPProvince());
    }

    /**
     * 获取IP所在地（省份）
     */
    public String getIPProvince() {
        String province = "";
        JSONObject json = new JSONObject();
        json.put("ak", StaticVariable.BAIDU_AK);
        json.put("ip", ServletUtil.getClientIp());
        JSONObject result =
                restTemplate.getForObject(
                        "http://api.map.baidu.com/location/ip?ak={ak}&ip={ip}&coor=bd09ll",
                        JSONObject.class,
                        json);
        logger.info(result.toString());
        logger.info(ServletUtil.getClientIp());
        if (result.getInteger("status").equals(0)) {
            province = String.valueOf(result.getJSONObject("content").getJSONObject("address_detail").get("province"));
        }
        return province;
    }

}
