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
        registry.addViewController("/").setViewName("user/index");
        registry.addViewController("/index.html").setViewName("user/index");
        registry.addViewController("/article.html").setViewName("user/article");
        registry.addViewController("/diary.html").setViewName("user/diary");
        registry.addViewController("/link.html").setViewName("user/link");
        registry.addViewController("/message.html").setViewName("user/message");
        registry.addViewController("/read.html").setViewName("user/read");
        registry.addViewController("/test.html").setViewName("test");
        registry.addViewController("/login.html").setViewName("user/login");

    }

    @Bean
    public LocaleResolver localeResolver() {
        return new MyLocalResolver();
    }

}
