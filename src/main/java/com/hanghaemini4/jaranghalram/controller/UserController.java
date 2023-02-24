package com.hanghaemini4.jaranghalram.controller;

import com.hanghaemini4.jaranghalram.dto.LoginRequestDto;
import com.hanghaemini4.jaranghalram.dto.SignupRequestDto;
import com.hanghaemini4.jaranghalram.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public String signup(@RequestBody SignupRequestDto signupRequestDto) {
        userService.signup(signupRequestDto);
        return "success";
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse httpServletResponse) {
        userService.login(loginRequestDto, httpServletResponse);
        return "success";
    }
    @GetMapping("/idCheck/{userName}")
    public String userNameCheck(@PathVariable String userName) {
        userService.userNameCheck(userName);
        return "success";
    }
    @GetMapping("/nickNameCheck/{userNickName}")
    public String userNickNameCheck(@PathVariable String userNickName) {
        userService.userNickNameCheck(userNickName);
        return "success";
    }

//    @GetMapping("/logout")
//    public String logout(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse httpServletResponse) {
//        userService.logout(loginRequestDto, httpServletResponse);
//        return "logout success";
//    }

}