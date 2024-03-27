package com.hblog.post.application;

import com.hblog.post.application.dto.PostDto;

public interface PostService {
    PostDto createPost(PostDto postDto);

    PostDto getPost(long postIdx, String role);
}
