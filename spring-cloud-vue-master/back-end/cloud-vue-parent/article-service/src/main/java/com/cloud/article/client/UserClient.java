package com.cloud.article.client;

import com.cloud.article.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// @FeignClient注解告诉Spring，这是一个用于调用其他微服务的客户端
// name = "cloud-user-service" 指定了我们要调用的服务在Eureka上的注册名
@FeignClient(name = "cloud-user-service")
public interface UserClient {

    // 这里的方法签名，必须和 user-service 中 UserController 里的方法完全对应
    // 包括请求方式(@GetMapping)、路径("/{id}")、和参数(@PathVariable)
    @GetMapping("/users/{id}")
    UserDTO findUserById(@PathVariable("id") Long id);
}
