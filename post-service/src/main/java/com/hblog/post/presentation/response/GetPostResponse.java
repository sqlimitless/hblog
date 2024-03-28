package com.hblog.post.presentation.response;

import com.hblog.post.domain.model.PostStatus;
import lombok.Getter;

@Getter
public class GetPostResponse {
    private Long idx;
    private String title;
    private String content;
    private PostStatus status;
}
