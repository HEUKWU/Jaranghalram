package com.hanghaemini4.jaranghalram.dto;

import com.hanghaemini4.jaranghalram.entity.Post;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class PostResponseDto {
    private Long id;
    private String title;
    private String content;
    private String imageUrl;
    private int postLikeCount;
    private String userName;

    private LocalDateTime regDt;
    private LocalDateTime modDt;


    public static PostResponseDto of(Post post) {
        return PostResponseDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .imageUrl(post.getImageUrl())
                .userName(post.getUser().getUserName())
                .regDt(post.getRegDt())
                .modDt(post.getModDt())
                .build();
    }
}
