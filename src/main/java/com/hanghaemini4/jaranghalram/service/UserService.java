package com.hanghaemini4.jaranghalram.service;

import com.hanghaemini4.jaranghalram.jwt.JwtUtil;
import com.hanghaemini4.jaranghalram.dto.LoginRequestDto;
import com.hanghaemini4.jaranghalram.dto.SignupRequestDto;
import com.hanghaemini4.jaranghalram.entity.User;
import com.hanghaemini4.jaranghalram.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
//    private static final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    @Transactional
    public String signup(SignupRequestDto signupRequestDto) {
        String userName = signupRequestDto.getUserName();
        String userNickName = signupRequestDto.getUserNickName();
        // 회원 아이디() 확인
        Optional<User> nameCheck = userRepository.findByUserName(userName);
        if (nameCheck.isPresent()) {
            throw new IllegalArgumentException("중복된 아이디가 존재합니다.");
        }
        // 회원 닉네임 확인
        Optional<User> nickNameCheck = userRepository.findByUserNickName(userNickName);
        if (nickNameCheck.isPresent()) {
            throw new IllegalArgumentException("이미 사용중인 닉네임입니다.");
        }
        User user = new User(signupRequestDto);
        userRepository.save(user);

        return "회원가입 성공";
    }
    public Boolean userNameCheck(String userName){
        Optional<User> nameCheck = userRepository.findByUserName(userName);
        if (nameCheck.isPresent()) {
            return false;
        }
        return true;
    }
    public Boolean userNickNameCheck(String userNickName){
        Optional<User> nameCheck = userRepository.findByUserNickName(userNickName);
        if (nameCheck.isPresent()) {
            return false;
        }
        return true;
    }

    @Transactional(readOnly = true)
    public void login(LoginRequestDto loginRequestDto, HttpServletResponse httpServletResponse) {
        String userName = loginRequestDto.getUserName();
        String password = loginRequestDto.getPassword();

        // 사용자 확인
        User user = userRepository.findByUserName(userName).orElseThrow(
                () -> new IllegalArgumentException("등록된 사용자가 없습니다.")
        );
        // 비밀번호 확인
        if(!user.getPassword().equals(password)){
            throw  new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        httpServletResponse.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUserName()));
    }

//    @Transactional(readOnly = true)
//    public void logout(LoginRequestDto loginRequestDto, HttpServletResponse httpServletResponse) {
//        String userName = loginRequestDto.getUserName();
//
//        httpServletResponse.setHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUserName()));
//    }

}