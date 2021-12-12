package cn.malong.blog;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

/**
 * EnableAsync  开启异步注解功能
 * EnableScheduling 开启基于注解的定时任务
 *
 * @author malong
 */
@Slf4j
@EnableAsync
@EnableScheduling
@SpringBootApplication
public class MyBlogMasterApplication implements ApplicationRunner {
    @Value("${server.port}")
    public int port;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(MyBlogMasterApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("已启动： http://localhost:" + port);
    }

}
