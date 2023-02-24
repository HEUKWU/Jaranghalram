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
    private String userName;
    @Size(min = 2, max = 10)
    private String userNickName;
    @Size(min = 8, max = 15)
    @Pattern(regexp = "[0-9a-zA-Z!@#$%^&*(),.?\":{}|<>]+]")
    private String password;
    public SignupRequestDto(String userName, String userNickName, String password){
        this.userName = userName;
        this.userNickName = userNickName;
        this.password = password;
    }
}