package com.hblog.post.domain.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "post")
@EntityListeners(AuditingEntityListener.class)
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(nullable = false, length = 200)
    private String title;

    @Lob
    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Long userIdx;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime regDate;

    @Column(nullable = false)
    @UpdateTimestamp
    private LocalDateTime modDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PostStatus status;

}

