package com.hanghaemini4.jaranghalram.dto;

import com.hanghaemini4.jaranghalram.entity.Comment;
import com.hanghaemini4.jaranghalram.entity.Post;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class PostOneResponseDto {
    private Long id;
    private String title;
    private String content;
    private String imageUrl;
    private int postLikeCount;
    private String userName;
    private boolean isLiked;

    private String createdAt;
    private String modifiedAt;

    private List<CommentResponseDto> commentList = new ArrayList<>();

    public PostOneResponseDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.imageUrl = post.getImageUrl();
        this.postLikeCount = post.getPostLikeCount();
        this.userName = post.getUser().getUserName();
        this.createdAt = post.getCreatedAt().toString();
        this.modifiedAt = post.getModifiedAt().toString();
        this.commentList = post.getCommentList().stream().sorted((a, b) ->
                        b.getModifiedAt().compareTo(a.getModifiedAt()))
                        .map(CommentResponseDto::new)
                        .collect(Collectors.toList());
    }

    public PostOneResponseDto(Post post, List<Comment> comments) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.imageUrl = post.getImageUrl();
        this.postLikeCount = post.getPostLikeCount();
        this.userName = post.getUser().getUserName();
        this.createdAt = post.getCreatedAt().toString();
        this.modifiedAt = post.getModifiedAt().toString();
        for (Comment comment : comments) {
            commentList.add(new CommentResponseDto(comment));
        }
    }
}
