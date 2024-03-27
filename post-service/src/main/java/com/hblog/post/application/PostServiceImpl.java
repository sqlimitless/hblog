package com.hblog.post.application;

import com.hblog.post.application.dto.PostDto;
import com.hblog.post.domain.model.Post;
import com.hblog.post.infrastructure.PostRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService{

    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public PostDto createPost(PostDto postDto) {
        Post post = modelMapper.map(postDto, Post.class);
        Post saved = postRepository.save(post);
        return modelMapper.map(saved, PostDto.class);
    }

    @Override
    public PostDto getPost(long postIdx, String role) {
        // 권한 확인
        // 조회
        // 리턴
        return null;
    }
}
