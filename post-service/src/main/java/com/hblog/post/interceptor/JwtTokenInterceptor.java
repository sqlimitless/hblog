package com.hblog.post.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

public class JwtTokenInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String token = request.getHeader("Authorization");
        String userId = parseToken(token);
        request.setAttribute("userId", userId);

        return true;
    }

    private String parseToken(String token) {
        // 토큰 파싱 로직 구현
        return "parsedUserId";
    }
}