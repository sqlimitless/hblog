package com.hblog.post.application.dto;

import com.hblog.post.domain.model.PostStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PostDto {

    private Long idx;
    private String title;
    private String content;
    private Long userIdx;
    private LocalDateTime regDate;
    private LocalDateTime modDate;
    private PostStatus status;
}
