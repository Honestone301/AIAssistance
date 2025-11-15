package com.aiassistance.Interceptor;

import com.aiassistance.Result.Result;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Date;
import java.util.Map;

import static com.aiassistance.Utill.JWT.parseToken;

@Component
public class JwtInterceptor implements HandlerInterceptor
{
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
        String token = request.getHeader("Authorization");
        if(token == null || token.isEmpty()){
            response.getWriter().write(Result.error(401, "未授权").toString());
            System.out.println("token is null");
            return false;
        }
        try {
            Map<String,Object> claims=  parseToken(token);
            // JWT 中常见的标准字段包括：
            // - "exp" (Expiration Time): 过期时间
            // - "iat" (Issued At): 签发时间
            // - "iss" (Issuer): 签发者
            // - "sub" (Subject): 主题
            // - "aud" (Audience): 接收方
            Date expiration=null;
            Object expObj = claims.get("exp");
            if(expObj != null){
                if (expObj instanceof Integer) {
                    // 如果是Integer类型，转换为Date
                    expiration = new Date((Integer) expObj * 1000L);
                } else if (expObj instanceof Long) {
                    // 如果是Long类型，转换为Date
                    expiration = new Date((Long) expObj * 1000L);
                } else if (expObj instanceof Date) {
                    // 如果已经是Date类型，直接使用
                    expiration = (Date) expObj;
                }
            }
            if(expiration!=null&&expiration.before(new Date())){
                // token 已过期
                response.setStatus(404);
                response.setContentType("application/json;charset=utf-8");
                response.getWriter().write("{\"code\":404,\"message\":\"令牌已过期\"}");
                return false;
            }
            request.setAttribute("userInfo", claims);
            return true;
        }catch(Exception e){
            response.getWriter().write(Result.error(401, "未授权")
                    .toString());
            System.out.println(e.getMessage());
            return false;
        }
    }
}
