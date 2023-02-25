package com.hanghaemini4.jaranghalram.controller;

import com.hanghaemini4.jaranghalram.dto.CommentRequestDto;
import com.hanghaemini4.jaranghalram.dto.CommentResponseDto;
import com.hanghaemini4.jaranghalram.dto.ResponseDto;
import com.hanghaemini4.jaranghalram.security.UserDetailsImpl;
import com.hanghaemini4.jaranghalram.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/api/comment/{postId}")
    public CommentResponseDto addComment(@PathVariable Long postId, @RequestBody CommentRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.add(postId, requestDto, userDetails.getUser());
    }

    @PutMapping("api/comment/{postId}")
    public ResponseDto<String> updateComment(@PathVariable Long postId, @RequestBody CommentRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.update(postId, requestDto, userDetails.getUser());
    }

    @DeleteMapping("api/comment/{postId}")
    public ResponseDto<String> deleteComment(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.delete(postId, userDetails.getUser());
    }
}
