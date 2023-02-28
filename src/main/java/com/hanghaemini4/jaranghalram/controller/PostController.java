package com.hanghaemini4.jaranghalram.controller;

import com.hanghaemini4.jaranghalram.dto.*;
import com.hanghaemini4.jaranghalram.entity.User;
import com.hanghaemini4.jaranghalram.security.UserDetailsImpl;
import com.hanghaemini4.jaranghalram.service.PostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
@Api(value = "게시글 api")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @ApiOperation(value = "게시글 목록 조회", notes = "page, size, sortBy로 페이징 후 조회")
    @GetMapping("/post")
    public ResponseDto<List<PostResponseDto>> getPostList(@ApiParam(value="page", required = true, example = "1") @RequestParam int page,
                                                          @ApiParam(value="size", required = true, example = "16")@RequestParam int size,
                                                          @ApiParam(value="sortBy", required = false,defaultValue = "createdAt", example = "PostLikeCount")@RequestParam(required = false, defaultValue = "createdAt") String sortBy,
    @GetMapping("/posts")
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

    @GetMapping("/posts/{postId}")
    public ResponseDto<PostOneResponseDto> getPost(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = null;
        if(userDetails != null) {
            user = userDetails.getUser();
        }
        return postService.getPost(postId, user);
    }

    @GetMapping("/posts/my-post-list")
    public ResponseDto<List<PostResponseDto>> getPostByUser(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.getMyList(userDetails.getUser());
    }

    @PostMapping("/posts")
    public ResponseDto<String> createPost(@RequestPart PostRequestDto requestDto, @RequestPart(value = "image") MultipartFile multipartFile, @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {
        return postService.create(requestDto, multipartFile, userDetails.getUser());
    }

    @PutMapping("/posts/{postId}")
    public ResponseDto<String> updatePost(@PathVariable Long postId,
                                     @RequestPart PostRequestDto requestDto,
                                     @RequestPart(value = "image") MultipartFile multipartFile, @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException{
        return postService.update(postId, requestDto, multipartFile, userDetails.getUser());
    }

    @DeleteMapping("/posts/{postId}")
    public ResponseDto<String> deletePost(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.delete(postId, userDetails.getUser());
    }

}
