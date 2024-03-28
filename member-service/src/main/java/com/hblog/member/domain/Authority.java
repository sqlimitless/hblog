package com.hblog.member.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "authority")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Authority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userIdx")
    private UserEntity user;

    @Enumerated(EnumType.STRING)
    private Role role;
}