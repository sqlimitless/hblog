package com.hblog.member.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Table(name = "users")
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserEntity {

    @Id
    @Column(name = "idx")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(name = "userId", length = 50, unique = true)
    private String userId;

    @Column(name = "password", length = 100)
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Authority> authority = new HashSet<>();

    public void addRole(Set<Role> roles) {
        roles.forEach(role ->
                this.authority.add(Authority.builder()
                    .user(this)
                    .role(role)
                    .build()));
    }
}