package com.hblog.post.presentation.request;

import com.hblog.post.domain.model.PostStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CreatePostRequest {
    @NotBlank
    private String title;
    @NotBlank
    private String content;
    private PostStatus status;
}
