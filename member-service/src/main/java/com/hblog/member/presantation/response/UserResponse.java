package com.hblog.member.presantation.response;

import com.hblog.member.jwt.Token;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse {
    private long idx;
    private String userId;
    private Token token;
}
