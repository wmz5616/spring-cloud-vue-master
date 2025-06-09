package com.cloud.user.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil {

    private final String SECRET_STRING = "my-super-secret-key-for-this-awesome-blog-project-that-is-definitely-long-enough";
    private Key key;
    private final long EXPIRATION_TIME = 1000 * 60 * 60 * 24; // 24小时

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(SECRET_STRING.getBytes());
    }

    // --- 以下是新增的方法 ---

    /**
     * 从Token中解析出用户名
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * 从Token中解析出过期时间
     */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * 验证Token是否有效
     */
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        // 验证用户名是否一致，并且Token是否未过期
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    /**
     * 从Token中解析出指定的Claim
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * 解析出Token中所有的Claims（负载）
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    /**
     * 检查Token是否已过期
     */
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }


    // --- 以下是原有的生成Token的方法 ---

    /**
     * 根据用户名生成Token
     */
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key)
                .compact();
    }
}
