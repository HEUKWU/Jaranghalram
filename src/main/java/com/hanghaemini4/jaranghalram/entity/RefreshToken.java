package com.hanghaemini4.jaranghalram.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Getter
@Entity
@NoArgsConstructor
public class RefreshToken extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String refreshToken;
    @NotBlank
    private String userNickName;

    public RefreshToken(String token, String userNickName) {
        this.refreshToken = token;
        this.userNickName = userNickName;
    }

    public RefreshToken updateToken(String token) {
        this.refreshToken = token;
        return this;
    }

}