package com.hanghaemini4.jaranghalram.controller;

import com.hanghaemini4.jaranghalram.dto.PostRequestDto;
import com.hanghaemini4.jaranghalram.dto.PostResponseDto;
import com.hanghaemini4.jaranghalram.dto.ResponseDto;
import com.hanghaemini4.jaranghalram.security.UserDetailsImpl;
import com.hanghaemini4.jaranghalram.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/post")
    public ResponseDto<List<PostResponseDto>> getPostList(@RequestParam int page,
                                                         @RequestParam int size,
                                                         @RequestParam(required = false, defaultValue = "regDt") String sortBy) {

        //sortBy = postLikeCount,
        return postService.getPostList(page-1, size, sortBy);
    }

    @PostMapping("/post")
    public ResponseDto<PostResponseDto> createPost(@RequestBody PostRequestDto requestDto, @RequestParam("file") MultipartFile multipartFile, UserDetailsImpl userDetails) {
        return postService.add(requestDto, multipartFile, userDetails.getUser());
    }


}
