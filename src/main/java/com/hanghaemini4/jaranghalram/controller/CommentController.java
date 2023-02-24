package com.hanghaemini4.jaranghalram.controller;

import com.hanghaemini4.jaranghalram.dto.CommentRequestDto;
import com.hanghaemini4.jaranghalram.dto.CommentResponseDto;
import com.hanghaemini4.jaranghalram.security.UserDetailsImpl;
import com.hanghaemini4.jaranghalram.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/api/comment/{id}")
    public CommentResponseDto addComment(@PathVariable Long id, @RequestBody CommentRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.add(id, requestDto, userDetails.getUser());
    }

    @PutMapping("api/comment/{id}")
    public void updateComment(@PathVariable Long id, @RequestBody CommentRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        commentService.update(id, requestDto, userDetails.getUser());
    }

    @DeleteMapping("api/comment/{id}")
    public void deleteComment(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        commentService.delete(id, userDetails.getUser());
    }
}
