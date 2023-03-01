package com.hanghaemini4.jaranghalram.dto;

import com.hanghaemini4.jaranghalram.entity.Post;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostResponseDto {
    private Long id;
    private String title;
    private String content;
    private String imageUrl;
    private int postLikeCount;
    private String userName;
    private Boolean isLiked;
    private String createdAt;
    private String modifiedAt;


    public static PostResponseDto of(Post post) {
        return PostResponseDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .imageUrl(post.getImageUrl())
                .postLikeCount(post.getPostLikeCount())
                .userName(post.getUser().getUserName())
                .createdAt(post.getCreatedAt().toString())
                .modifiedAt(post.getModifiedAt().toString())
                .build();
    }

    public void setLiked(boolean liked) {
        isLiked = liked;
    }
}
