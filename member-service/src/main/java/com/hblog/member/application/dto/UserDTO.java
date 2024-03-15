package com.hblog.member.application.dto;

import lombok.Getter;

@Getter
public class UserDTO {

    private long idx;
    private String userId;
    private String password;
    private String role;
}
