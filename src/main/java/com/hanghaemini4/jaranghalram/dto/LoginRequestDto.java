package com.hanghaemini4.jaranghalram.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class LoginRequestDto {
    private String userName;
    private String password;
}