package com.hanghaemini4.jaranghalram.controller;

import com.hanghaemini4.jaranghalram.dto.*;
import com.hanghaemini4.jaranghalram.entity.User;
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
                                                          @RequestParam(required = false, defaultValue = "createdAt") String sortBy,
                                                          @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = null;
        if(userDetails != null) {
            user = userDetails.getUser();
        }
        //sortBy = postLikeCount,
        return postService.getPostList(page-1, size, sortBy, user);
    }

    @GetMapping("/post/{postId}")
    public ResponseDto<PostOneResponseDto> getPost(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = null;
        if(userDetails != null) {
            user = userDetails.getUser();
        }
        return postService.getPost(postId, user);
    }

    @GetMapping("/post/myPostList")
    public ResponseDto<List<PostResponseDto>> getMyPostList(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.getMyList(userDetails.getUser());
    }

    @PostMapping("/post")
    public ResponseDto<?> createPost(@RequestPart(value = "title") String title,
                                     @RequestPart(value = "content") String content,
                                     @RequestPart(value = "image") MultipartFile multipartFile, @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {
        PostRequestDto requestDto = new PostRequestDto(multipartFile, content, title);
        return postService.addPost(requestDto, userDetails.getUser());
    }

    @PutMapping("/post/{postId}")
    public ResponseDto<?> updatePost(@PathVariable Long postId,
                                          @RequestParam(value = "title") String title,
                                          @RequestParam(value = "content") String content,
                                          @RequestParam(value = "image") MultipartFile multipartFile, @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException{
        PostRequestDto requestDto = new PostRequestDto(multipartFile, content, title);
        return postService.updatePost(postId, requestDto, userDetails.getUser());
    }

    @DeleteMapping("/post/{postId}")
    public ResponseDto<?> deletePost(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.deletePost(postId, userDetails.getUser());
    }

}
