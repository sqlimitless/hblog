package com.hblog.member.presantation.request;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class UserRequest {
    private long idx;
    private String userId;
    private String password;
}
