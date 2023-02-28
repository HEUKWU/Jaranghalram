package com.hanghaemini4.jaranghalram.service;

import com.hanghaemini4.jaranghalram.dto.ResponseDto;
import com.hanghaemini4.jaranghalram.dto.TokenDto;
import com.hanghaemini4.jaranghalram.entity.RefreshToken;
import com.hanghaemini4.jaranghalram.exceptionHandler.CustomException;
import com.hanghaemini4.jaranghalram.exceptionHandler.ErrorCode;
import com.hanghaemini4.jaranghalram.jwt.JwtUtil;
import com.hanghaemini4.jaranghalram.dto.LoginRequestDto;
import com.hanghaemini4.jaranghalram.dto.SignupRequestDto;
import com.hanghaemini4.jaranghalram.entity.User;
import com.hanghaemini4.jaranghalram.repository.RefreshTokenRepository;
import com.hanghaemini4.jaranghalram.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public ResponseDto<Boolean> signup(SignupRequestDto signupRequestDto) {
        String userName = signupRequestDto.getUserName();
        String userNickName = signupRequestDto.getUserNickName();
        String password = passwordEncoder.encode(signupRequestDto.getPassword());
        // 회원 아이디() 확인
        Optional<User> nameCheck = userRepository.findByUserName(userName);
        if (nameCheck.isPresent()) {
            throw new CustomException(ErrorCode.DuplicateUsername);
        }
        // 회원 닉네임 확인
        Optional<User> nickNameCheck = userRepository.findByUserNickName(userNickName);
        if (nickNameCheck.isPresent()) {
            throw new CustomException(ErrorCode.DuplicatedNickname);
        }

        User user = new User(signupRequestDto, password);
        userRepository.save(user);

        return ResponseDto.success(null);
    }

    public ResponseDto<Boolean> userNameCheck(String userName) {
        Optional<User> nameCheck = userRepository.findByUserName(userName);
        if (nameCheck.isPresent()) {
            throw new CustomException(ErrorCode.DuplicateUsername);
        }
        return ResponseDto.success(null);
    }

    public ResponseDto<Boolean> userNickNameCheck(String userNickName) {
        Optional<User> nameCheck = userRepository.findByUserNickName(userNickName);
        if (nameCheck.isPresent()) {
            throw new CustomException(ErrorCode.DuplicatedNickname);
        }
        return ResponseDto.success(null);

    }

    @Transactional
    public ResponseDto<Boolean> login(LoginRequestDto loginRequestDto, HttpServletResponse httpServletResponse) {
        String userName = loginRequestDto.getUserName();
        String password = loginRequestDto.getPassword();

        // 사용자 확인
        User user = userRepository.findByUserName(userName).orElseThrow(
                () -> new CustomException(ErrorCode.NotFoundUser)
        );
        // 비밀번호 확인
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new CustomException(ErrorCode.NotMatchPassword);
        }
        TokenDto tokenDto = jwtUtil.createAllToken(user.getUserNickName());
//        httpServletResponse.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUserNickName(),JwtUtil.ACCESS_TOKEN_TIME));
//        httpServletResponse.addHeader(JwtUtil.REFRESH_TOKEN_HEADER, jwtUtil.createToken(user.getUserNickName(),JwtUtil.REFRESH_TOKEN_TIME));
        Optional<RefreshToken> refreshToken = refreshTokenRepository.findByUserNickName(user.getUserNickName());

        if (refreshToken.isPresent()) {
            refreshTokenRepository.save(refreshToken.get().updateToken(tokenDto.getRefreshToken()));
        } else {
            RefreshToken newToken = new RefreshToken(tokenDto.getRefreshToken(), user.getUserNickName());
            refreshTokenRepository.save(newToken);
        }

        setHeader(httpServletResponse, tokenDto);
        return ResponseDto.success(null);
    }

    @Transactional
    public ResponseDto<Boolean> isRefreshToken(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        String refreshToken = jwtUtil.resolveToken(httpServletRequest, "RefreshToken");
        if (!jwtUtil.refreshTokenValidation(refreshToken)) {
            throw new CustomException(ErrorCode.RefreshTokenValidException);
        }
        httpServletResponse.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(jwtUtil.getUserNickName(refreshToken), JwtUtil.ACCESS_TOKEN_TIME));

        return ResponseDto.success(null);
    }

    private void setHeader(HttpServletResponse httpServletResponse, TokenDto tokenDto) {
        httpServletResponse.addHeader(JwtUtil.AUTHORIZATION_HEADER, tokenDto.getAuthorization());
        httpServletResponse.addHeader(JwtUtil.REFRESH_TOKEN_HEADER, tokenDto.getRefreshToken());
    }

}