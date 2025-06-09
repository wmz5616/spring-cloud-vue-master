package com.cloud.user.config;

import com.cloud.user.security.JwtRequestFilter; // 导入我们的过滤器
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy; // 导入SessionCreationPolicy
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter; // 导入UsernamePasswordAuthenticationFilter

import javax.ws.rs.HttpMethod;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired // 注入我们自己写的JWT过滤器
    private JwtRequestFilter jwtRequestFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // 关闭CSRF
                .authorizeRequests()
                .antMatchers("/auth/**").permitAll() // 放行/auth/下的所有请求
                .antMatchers(HttpMethod.GET, "/users/**").permitAll()
                .anyRequest().authenticated() // 其他所有请求都需要认证
                .and()
                // 关键修改1：告诉Spring Security不要创建和使用Session
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // 关键修改2：告诉Spring Security在执行常规的用户名密码认证前，先执行我们自定义的JWT过滤器
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
