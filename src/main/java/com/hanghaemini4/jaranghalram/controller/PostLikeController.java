package com.hanghaemini4.jaranghalram.controller;

import com.hanghaemini4.jaranghalram.dto.ResponseDto;
import com.hanghaemini4.jaranghalram.security.UserDetailsImpl;
import com.hanghaemini4.jaranghalram.service.PostLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PostLikeController {
    private final PostLikeService postLikeService;

    @GetMapping("/like-counts/{postId}")
    public ResponseDto<Integer> likeCountsCheck(@PathVariable Long postId) {
        return postLikeService.likeCountsCheck(postId);
    }
    @PostMapping("/like-counts/{postId}")
    public ResponseDto<Boolean> likePost(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postLikeService.likePost(postId, userDetails.getUser());
    }
}