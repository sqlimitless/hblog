package com.hblog.post.presentation;

import com.hblog.post.application.PostService;
import com.hblog.post.application.dto.PostDto;
import com.hblog.post.presentation.request.CreatePostRequest;
import com.hblog.post.presentation.response.GetPostResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Set;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final ModelMapper modelMapper;

    @PostMapping("/post")
    public ResponseEntity<URI> createPost(@Validated @RequestBody CreatePostRequest createPostRequest, @RequestHeader("userIdx") Long userIdx) {
        PostDto postDto = modelMapper.map(createPostRequest, PostDto.class);
        postDto.setUserIdx(userIdx);
        PostDto saved = postService.createPost(postDto);
        return ResponseEntity.created(URI.create("/post/" + saved.getIdx())).build();
    }

    @GetMapping("/post/{postIdx}")
    public ResponseEntity<?> getPost(@PathVariable("postIdx") long postIdx, @RequestHeader("roles") Set<String> role) {
        // 조회
        PostDto postDto = postService.getPost(postIdx, role);
        // 리턴
        return ResponseEntity.ok(modelMapper.map(postDto, GetPostResponse.class));
    }
}
