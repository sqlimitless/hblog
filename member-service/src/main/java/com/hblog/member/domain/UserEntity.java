package com.hblog.member.domain;

import jakarta.persistence.*;
import lombok.*;

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

    @Column(name = "user_id", length = 50, unique = true)
    private String userId;

    @Column(name = "password", length = 100)
    private String password;
}
