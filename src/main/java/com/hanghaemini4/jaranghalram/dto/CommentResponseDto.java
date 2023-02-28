package com.hanghaemini4.jaranghalram.dto;

import com.hanghaemini4.jaranghalram.entity.Comment;
import com.hanghaemini4.jaranghalram.entity.User;
import lombok.Getter;

@Getter
public class CommentResponseDto {

    private Long id;
    private String content;
    private String userName;
    private String createdAt;
    private String modifiedAt;
    private Long postId;

    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getCommentContent();
        this.userName = comment.getUser().getUserName();
        this.createdAt = comment.getCreatedAt().toString();
        this.modifiedAt = comment.getModifiedAt().toString();
        this.postId = comment.getPost().getId();
    }

    public CommentResponseDto(Comment comment, User user) {
        this.id = comment.getId();
        this.content = comment.getCommentContent();
        this.userName = user.getUserName();
        this.createdAt = comment.getCreatedAt().toString();
        this.modifiedAt = comment.getModifiedAt().toString();
        this.postId = comment.getPost().getId();
    }
}
