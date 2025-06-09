package com.cloud.article;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients; // 导入EnableFeignClients

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients // 关键注解：开启Feign客户端功能，允许此服务调用其他服务
public class ArticleServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ArticleServiceApplication.class, args);
    }

}
