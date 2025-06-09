package com.cloud.user.controller;

import com.cloud.user.domain.User;
import com.cloud.user.repository.UserRepository;
import com.cloud.user.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
// 1. 我们删除了类级别的 @RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    // 2. 将路径 "/auth" 加到了方法上
    @PostMapping("/auth/register")
    public User register(@RequestBody User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        return userRepository.save(user);
    }

    // 3. 将路径 "/auth" 加到了方法上
    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody User loginRequest) {
        return userRepository.findByUsername(loginRequest.getUsername())
                .map(userDetails -> {
                    if (passwordEncoder.matches(loginRequest.getPassword(), userDetails.getPassword())) {
                        String token = jwtUtil.generateToken(userDetails.getUsername());
                        Map<String, String> tokenMap = new HashMap<>();
                        tokenMap.put("token", token);
                        return ResponseEntity.ok(tokenMap);
                    }
                    return ResponseEntity.status(401).body("用户名或密码错误");
                })
                .orElse(ResponseEntity.status(401).body("用户名或密码错误"));
    }
}
