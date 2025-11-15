package com.aiassistance.Utill;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

@Component
public  class JWT {
    private static final String SECRET_KEY = System.getenv("jwt.secret"); // 密钥
    //  JWT 过期时间 6 小时
    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 6;

    public static String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    // 优化verify方法，利用parseToken避免重复代码
    public static boolean verify(String token) {
        try {
            parseToken(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    // 解析 JWT，修正方法名拼写
    public static Map<String, Object> parseToken(String token) {
        try {
            // 添加token非空检查
            if (token == null || token.trim().isEmpty()) {
                throw new IllegalArgumentException("Token cannot be null or empty");
            }

            return Jwts.parserBuilder() // 使用推荐的parserBuilder()而非parser()
                    .setSigningKey(SECRET_KEY.getBytes()) // 使用字节数组形式的密钥
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            // 记录异常信息，有助于调试
            System.err.println("JWT parsing failed: " + e.getMessage());
            // 可以选择抛出更具体的异常
            throw new RuntimeException("Invalid token: " + e.getMessage(), e);
        }
    }
}
