package com.cloud.user.domain;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections; // 导入Collections

@Data
@Entity
@Table(name = "tb_user")
// 👇 关键修改1：实现 UserDetails 接口
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    // 以下是实现 UserDetails 接口需要添加的方法

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 暂时返回一个空的角色列表，以后可以扩展
        return Collections.emptyList();
    }

    @Override
    public boolean isAccountNonExpired() {
        // 账户是否未过期
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // 账户是否未被锁定
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // 凭证（密码）是否未过期
        return true;
    }

    @Override
    public boolean isEnabled() {
        // 账户是否启用
        return true;
    }
}
