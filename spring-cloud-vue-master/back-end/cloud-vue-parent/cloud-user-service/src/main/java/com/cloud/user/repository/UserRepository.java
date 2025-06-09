package com.cloud.user.repository;

import com.cloud.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional; // 导入Optional

public interface UserRepository extends JpaRepository<User, Long> {

    // 👇 添加这个方法定义
    // Spring Data JPA会根据方法名自动为我们实现查询逻辑
    Optional<User> findByUsername(String username);

}
