package com.hanghaemini4.jaranghalram.controller;

import com.hanghaemini4.jaranghalram.dto.LoginRequestDto;
import com.hanghaemini4.jaranghalram.dto.ResponseDto;
import com.hanghaemini4.jaranghalram.dto.SignupRequestDto;
import com.hanghaemini4.jaranghalram.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseDto<Boolean> signup(@RequestBody @Valid SignupRequestDto signupRequestDto) {
        return userService.signup(signupRequestDto);
    }

    @PostMapping("/login")
    public ResponseDto<Boolean> login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse httpServletResponse) {
        return userService.login(loginRequestDto, httpServletResponse);
    }
    @GetMapping("/idCheck/{userName}")
    public ResponseDto<Boolean> userNameCheck(@PathVariable String userName) {
        return userService.userNameCheck(userName);
    }
    @GetMapping("/nick-name/{userNickName}")
    public ResponseDto<Boolean> userNickNameCheck(@PathVariable String userNickName) {
        return userService.userNickNameCheck(userNickName);
    }

    @PostMapping("/isRefreshToken")
    public ResponseDto<Boolean> isRefreshToken(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        return userService.isRefreshToken(httpServletRequest, httpServletResponse);
    }

//    @GetMapping("/logout")
//    public String logout(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse httpServletResponse) {
//        userService.logout(loginRequestDto, httpServletResponse);
//        return "logout success";
//    }

}