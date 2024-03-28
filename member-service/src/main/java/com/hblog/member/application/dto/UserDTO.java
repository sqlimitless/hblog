package com.hblog.member.application.dto;

import com.hblog.member.domain.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class UserDTO {

    private long idx;
    private String userId;
    private String password;
    private Set<Role> roles;
}
