package com.hblog.member.jwt;


import com.hblog.member.domain.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
public class JwtTokenProvider {

    @Value("${token.accessTokenValidTime}")
    private String accessTokenValidTime;

    @Value("${token.refreshTokenValidTime}")
    private String refreshTokenValidTime;

    @Value("${token.secretKey}")
    private String secretKey;

    public Token createAccessToken(Long userIdx, String userId, Set<Role> roles) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date();
        Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        Map<String, Object> headerMap = new HashMap<>();
        headerMap.put("alg", "HS256");
        headerMap.put("typ", "JWT");
        Claims claims = Jwts.claims().setSubject(userIdx.toString());

        String refreshToken = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(nowMillis + Long.parseLong(refreshTokenValidTime)))
                .signWith(key)
                .compact();
        if (roles != null) {
            claims.put("roles", roles);
        }
        claims.put("userId", userId);
        String accessToken = Jwts.builder()
                .setHeader(headerMap)
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(nowMillis + Long.parseLong(accessTokenValidTime)))
                .signWith(key)
                .compact();

        return Token.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public Claims parseJwt(String token) {
        Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        return Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

}
