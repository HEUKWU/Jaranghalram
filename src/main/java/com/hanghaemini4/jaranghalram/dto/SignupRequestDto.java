package com.hanghaemini4.jaranghalram.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;
import javax.validation.constraints.Pattern;

@Setter
@Getter
public class SignupRequestDto {
    @Size(min = 4, max = 10)
    @Pattern(regexp = "[0-9a-z]+]")
    private String username;
    @Size(min = 8, max = 15)
    @Pattern(regexp = "[0-9a-zA-Z!@#$%^&*(),.?\":{}|<>]+]")
    private String password;
    public SignupRequestDto(String username, String password){
        this.username = username;
        this.password = password;
    }
}