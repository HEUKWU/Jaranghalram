package com.hanghaemini4.jaranghalram.controller;

import com.hanghaemini4.jaranghalram.dto.PostOneResponseDto;
import com.hanghaemini4.jaranghalram.dto.PostRequestDto;
import com.hanghaemini4.jaranghalram.dto.PostResponseDto;
import com.hanghaemini4.jaranghalram.dto.ResponseDto;
import com.hanghaemini4.jaranghalram.security.UserDetailsImpl;
import com.hanghaemini4.jaranghalram.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/post")
    public ResponseDto<List<PostResponseDto>> getPostList(@RequestParam int page,
                                                          @RequestParam int size,
                                                          @RequestParam(required = false, defaultValue = "createdAt") String sortBy) {

        //sortBy = postLikeCount,
        return postService.getPostList(page-1, size, sortBy);
    }

    @GetMapping("/post/{postId}")
    public ResponseDto<PostOneResponseDto> getPost(@PathVariable Long postId) {
        return postService.getPost(postId);
    }

    @PostMapping("/post")
    public ResponseDto<String> createPost(@RequestBody PostRequestDto requestDto, @RequestParam("file") MultipartFile multipartFile, UserDetailsImpl userDetails) throws IOException {
        return postService.addPost(requestDto, multipartFile, userDetails.getUser());
    }

    @PutMapping("/post/{postId}")
    public ResponseDto<String> updatePost(@PathVariable Long postId, PostRequestDto requestDto, @RequestParam("file") MultipartFile multipartFile, @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException{
        return postService.updatePost(postId, requestDto, multipartFile, userDetails.getUser());
    }

    @DeleteMapping("/post/{postId}")
    public ResponseDto<String> deletePost(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.deletePost(postId, userDetails.getUser());
    }

}
