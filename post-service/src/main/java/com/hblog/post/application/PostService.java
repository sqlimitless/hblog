package com.hblog.post.application;

import com.hblog.post.application.dto.PostDto;

import java.util.Set;

public interface PostService {
    PostDto createPost(PostDto postDto);

    PostDto getPost(long postIdx, Set<String> role);
}
