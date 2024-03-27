package com.hblog.post.domain.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@Table(name = "member")
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(nullable = false)
    private Long userIdx;

    @Column(nullable = false)
    private String userId;
}
