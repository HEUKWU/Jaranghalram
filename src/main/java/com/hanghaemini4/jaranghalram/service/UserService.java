package com.hanghaemini4.jaranghalram.service;

import com.hanghaemini4.jaranghalram.dto.ResponseDto;
import com.hanghaemini4.jaranghalram.exceptionHandler.UserServiceException;
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

    @Transactional
    public ResponseDto<Boolean> signup(SignupRequestDto signupRequestDto) {
        String userName = signupRequestDto.getUserName();
        String userNickName = signupRequestDto.getUserNickName();
        // 회원 아이디() 확인
        Optional<User> nameCheck = userRepository.findByUserName(userName);
        if (nameCheck.isPresent()) {
            throw new UserServiceException("중복된 아이디가 존재합니다.");
        }
        // 회원 닉네임 확인
        Optional<User> nickNameCheck = userRepository.findByUserNickName(userNickName);
        if (nickNameCheck.isPresent()) {
            throw new UserServiceException("이미 사용중인 닉네임입니다.");
        }
        User user = new User(signupRequestDto);
        userRepository.save(user);

        return ResponseDto.success(null);
    }
    public ResponseDto<Boolean> userNameCheck(String userName){
        Optional<User> nameCheck = userRepository.findByUserName(userName);
        if (nameCheck.isPresent()) {
            throw new UserServiceException("userName이 중복됩니다.");
        }
        return ResponseDto.success(null);
    }
    public ResponseDto<Boolean> userNickNameCheck(String userNickName){
        Optional<User> nameCheck = userRepository.findByUserNickName(userNickName);
        if (nameCheck.isPresent()) {
            throw new UserServiceException("userNickName이 중복됩니다.");
        }
        return ResponseDto.success(null);

    }

    @Transactional(readOnly = true)
    public void login(LoginRequestDto loginRequestDto, HttpServletResponse httpServletResponse) {
        String userName = loginRequestDto.getUserName();
        String password = loginRequestDto.getPassword();

        // 사용자 확인
        User user = userRepository.findByUserName(userName).orElseThrow(
                () -> new UserServiceException("등록된 사용자가 없습니다.")
        );
        // 비밀번호 확인
        if(!user.getPassword().equals(password)){
            throw  new UserServiceException("비밀번호가 일치하지 않습니다.");
        }

        httpServletResponse.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUserName()));
    }

}