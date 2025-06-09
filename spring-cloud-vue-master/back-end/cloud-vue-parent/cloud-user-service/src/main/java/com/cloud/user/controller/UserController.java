package com.cloud.user.controller;

import com.cloud.user.domain.User;
import com.cloud.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
// 1. 我们删除了类级别的 @RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // 2. 将路径 "/users" 加到了方法上
    @GetMapping("/users")
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    // 3. 将路径 "/users" 加到了方法上
    @GetMapping("/users/{id}")
    public User findUserById(@PathVariable Long id) {
        return userRepository.findById(id).orElse(null);
    }
}
