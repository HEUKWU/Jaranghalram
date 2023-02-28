package com.hanghaemini4.jaranghalram.entity;

import com.hanghaemini4.jaranghalram.dto.SignupRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity(name = "users")
public class User {

    @Id
    @Column(name = "userId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String userName;
    @Column(nullable = false, unique = true)
    private String userNickName;
    @Column(nullable = false)
    private String password;

    public User(SignupRequestDto requestDto, String password) {
        this.userName = requestDto.getUserName();
        this.userNickName = requestDto.getUserNickName();
        this.password = password;
    }

}