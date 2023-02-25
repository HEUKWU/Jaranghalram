package com.hanghaemini4.jaranghalram.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostRequestDto {
    private MultipartFile multipartFile;
    private String title;
    private String content;
}
