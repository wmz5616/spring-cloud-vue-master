package com.cloud.article.dto;

import lombok.Data;

// 这个类只包含我们需要的用户信息（比如，我们不希望把用户的密码也传来传去）
@Data
public class UserDTO {
    private Long id;
    private String username;
}
