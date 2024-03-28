package com.hblog.post.application;

import com.hblog.post.application.dto.PostDto;
import com.hblog.post.domain.model.Post;
import com.hblog.post.infrastructure.PostRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

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
    public PostDto getPost(long postIdx, Set<String> role) {
        // 조회
        Post post = postRepository.findById(postIdx).orElseThrow();
        // TODO 권한 확인
        // 리턴
        return modelMapper.map(post, PostDto.class);
    }
}
