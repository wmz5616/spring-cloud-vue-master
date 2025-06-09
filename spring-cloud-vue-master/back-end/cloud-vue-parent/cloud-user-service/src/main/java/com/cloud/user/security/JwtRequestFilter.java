package com.cloud.user.security;

import com.cloud.user.service.MyUserDetailsService;
import com.cloud.user.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        final String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;

        // 1. 检查Authorization请求头是否存在，并且是否以"Bearer "开头
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            username = jwtUtil.extractUsername(jwt); // 我们需要在JwtUtil中添加这个方法
        }

        // 2. 如果成功获取到用户名，并且当前安全上下文中没有用户认证信息
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // 3. 根据用户名加载用户信息
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            // 4. 验证Token是否有效
            if (jwtUtil.validateToken(jwt, userDetails)) { // 我们需要在JwtUtil中添加这个方法
                // 5. 如果Token有效，则手动设置认证信息到Spring Security上下文中
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        // 6. 继续执行过滤器链中的下一个过滤器
        chain.doFilter(request, response);
    }
}
