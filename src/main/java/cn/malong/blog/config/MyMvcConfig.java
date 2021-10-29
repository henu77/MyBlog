package cn.malong.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author malong
 */
@Configuration
public class MyMvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {

        //首页
        registry.addViewController("/").setViewName("user/index");
        registry.addViewController("/index.html").setViewName("user/index");

        //公共页首页
        registry.addViewController("/article.html").setViewName("user/article");
        registry.addViewController("/diary.html").setViewName("user/diary");
        registry.addViewController("/link.html").setViewName("user/link");
        registry.addViewController("/message.html").setViewName("user/message");
        registry.addViewController("/read.html").setViewName("user/read");
//        registry.addViewController("/test.html").setViewName("test");
        registry.addViewController("/login.html").setViewName("user/login");

        //admin页面
        registry.addViewController("/admin/index").setViewName("admin/index");
        registry.addViewController("/admin/index.html").setViewName("admin/index");
        registry.addViewController("/admin/welcome.html").setViewName("admin/welcome");
        registry.addViewController("/admin/user.html").setViewName("admin/user");
        registry.addViewController("/admin/write.html").setViewName("admin/write");
    }

    @Bean
    public LocaleResolver localeResolver() {
        return new MyLocalResolver();
    }

}
