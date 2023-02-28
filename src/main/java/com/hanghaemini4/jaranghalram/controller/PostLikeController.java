package com.hanghaemini4.jaranghalram.controller;

import com.hanghaemini4.jaranghalram.dto.ResponseDto;
import com.hanghaemini4.jaranghalram.security.UserDetailsImpl;
import com.hanghaemini4.jaranghalram.service.PostLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PostLikeController {
    private final PostLikeService postLikeService;

    @PostMapping("/like-counts/{postId}")
    public ResponseDto<Boolean> likePost(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postLikeService.likePost(postId, userDetails.getUser());
    }
}