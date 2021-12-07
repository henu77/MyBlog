package cn.malong.blog.config;

import cn.malong.blog.interceptor.AdminIndexHandlerInterceptor;
import cn.malong.blog.interceptor.TrafficStatisticsInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author malong
 */
@Configuration
public class MyMvcConfig implements WebMvcConfigurer {

    @Autowired
    private AdminIndexHandlerInterceptor adminIndexHandlerInterceptor;
    @Autowired
    private TrafficStatisticsInterceptor trafficStatisticsInterceptor;

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {

        //首页
        registry.addViewController("/").setViewName("user/index");
        registry.addViewController("/index.html").setViewName("user/index");

        //公共页首页
        registry.addViewController("/article.html").setViewName("user/article");
        registry.addViewController("/articleSearch.html").setViewName("user/articleSearch");
        registry.addViewController("/articleByType.html").setViewName("user/articleByType");
        registry.addViewController("/link.html").setViewName("user/link");
        registry.addViewController("/message.html").setViewName("user/message");
        registry.addViewController("/read.html").setViewName("user/read");
        registry.addViewController("/login.html").setViewName("user/login");
        registry.addViewController("/register.html").setViewName("user/register");
        registry.addViewController("/miniLogin.html").setViewName("user/miniLogin");
        registry.addViewController("/user-setting.html").setViewName("user/user-setting");
        registry.addViewController("/profile.html").setViewName("user/profile");
        registry.addViewController("/password.html").setViewName("user/password");
        registry.addViewController("/ad-edit.html").setViewName("user/ad-edit");
        registry.addViewController("/auth-register.html").setViewName("user/auth-register");
        registry.addViewController("/binding.html").setViewName("user/binding");

        registry.addViewController("/404.html").setViewName("error/404");

        //admin页面
        registry.addViewController("/admin/index").setViewName("admin/index");
        registry.addViewController("/admin/index.html").setViewName("admin/index");
        registry.addViewController("/admin/welcome.html").setViewName("admin/welcome");
        registry.addViewController("/admin/user.html").setViewName("admin/user");
        registry.addViewController("/admin/adminUser.html").setViewName("admin/adminUser");
        registry.addViewController("/admin/write.html").setViewName("admin/write");
        registry.addViewController("/admin/updateBlog.html").setViewName("admin/updateBlog");
        registry.addViewController("/admin/user-setting.html").setViewName("admin/user-setting");
        registry.addViewController("/admin/password.html").setViewName("admin/password");
        registry.addViewController("/admin/profile.html").setViewName("admin/profile");
        registry.addViewController("/admin/comments.html").setViewName("admin/comments");
        registry.addViewController("/admin/comment-edit.html").setViewName("admin/comment-edit");
        registry.addViewController("/admin/blogs.html").setViewName("admin/blogs");
        registry.addViewController("/admin/types.html").setViewName("admin/types");
        registry.addViewController("/admin/type-add.html").setViewName("admin/type-add");
        registry.addViewController("/admin/type-update.html").setViewName("admin/type-update");
        registry.addViewController("/admin/user-edit.html").setViewName("admin/user-edit");
        registry.addViewController("/admin/user-add.html").setViewName("admin/user-add");
        registry.addViewController("/admin/links.html").setViewName("admin/links");

    }

    @Bean
    public LocaleResolver localeResolver() {
        return new MyLocalResolver();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(adminIndexHandlerInterceptor)
                .addPathPatterns("/admin/**")
                .excludePathPatterns("/", "/index.html", "/admin/lib/**");
        registry.addInterceptor(trafficStatisticsInterceptor)
                .addPathPatterns("/user/toArticle**", "/user/toReadBlog/**",
                        "/user/toArticleByType/**", "/user/login**");
    }
}
