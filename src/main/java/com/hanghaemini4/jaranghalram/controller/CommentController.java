package com.hanghaemini4.jaranghalram.controller;

import com.hanghaemini4.jaranghalram.dto.CommentRequestDto;
import com.hanghaemini4.jaranghalram.dto.CommentResponseDto;
import com.hanghaemini4.jaranghalram.dto.ResponseDto;
import com.hanghaemini4.jaranghalram.security.UserDetailsImpl;
import com.hanghaemini4.jaranghalram.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping("api/comments/my-comment-list")
    public ResponseDto<List<CommentResponseDto>> getCommentByUser(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return commentService.getCommentList(userDetails.getUser());
    }

    @GetMapping("/api/comments/{postId}")
    public ResponseDto<List<CommentResponseDto>> getCommentByPost(@PathVariable Long postId) {
        return commentService.getCommentCurrentList(postId);
    }

    @PostMapping("/api/comments/{postId}")
    public ResponseDto<String> createComment(@PathVariable Long postId, @RequestBody CommentRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.create(postId, requestDto, userDetails.getUser());
    }

    @PutMapping("api/comments/{postId}")
    public ResponseDto<String> updateComment(@PathVariable Long postId, @RequestBody CommentRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.update(postId, requestDto, userDetails.getUser());
    }

    @DeleteMapping("api/comments/{postId}")
    public ResponseDto<String> deleteComment(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.delete(postId, userDetails.getUser());
    }
}
