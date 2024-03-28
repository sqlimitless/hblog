package com.hblog.member.presantation.request;

import com.hblog.member.domain.Role;
import lombok.Getter;
import lombok.ToString;

import java.util.Set;

@Getter
public class UserRequest {
    private long idx;
    private String userId;
    private String password;
    private Set<Role> roles;
}
