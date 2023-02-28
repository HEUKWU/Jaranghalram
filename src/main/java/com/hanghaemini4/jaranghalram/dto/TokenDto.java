package com.hanghaemini4.jaranghalram.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TokenDto {

    private String Authorization;
    private String RefreshToken;

    public TokenDto(String accessToken, String refreshToken) {
        this.Authorization = accessToken;
        this.RefreshToken = refreshToken;
    }

}