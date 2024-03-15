package com.hblog.member.jwt;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class Token {
    private String accessToken;
    private String refreshToken;
}