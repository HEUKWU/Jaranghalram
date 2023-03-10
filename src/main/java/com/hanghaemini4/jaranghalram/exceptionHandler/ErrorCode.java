package com.hanghaemini4.jaranghalram.exceptionHandler;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {
    UnAuthorized(HttpStatus.UNAUTHORIZED, "로그인을 해주세요."),
    NotFoundUser(HttpStatus.BAD_REQUEST, "아이디가 존재하지 않습니다."),
    NotMatchPassword(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다."),
    DuplicateUsername(HttpStatus.BAD_REQUEST, "이미 존재하는 아이디 입니다."),
    DuplicatedNickname(HttpStatus.BAD_REQUEST, "이미 존재하는 닉네임 입니다."),
    NotMatchUser(HttpStatus.BAD_REQUEST, "작성자가 일치하지 않습니다."),
    NotFoundPost(HttpStatus.NOT_FOUND, "게시물을 찾을 수 없습니다."),
    NotFoundComment(HttpStatus.NOT_FOUND, "댓글이 존재하지 않습니다."),
    NoModifyPermission(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다."),
    NoDeletePermission(HttpStatus.BAD_REQUEST, "삭제 권한이 없습니다."),
    InValidException(HttpStatus.BAD_REQUEST, "값이 잘못되었습니다."),
    TokenSecurityExceptionOrMalformedJwtException(HttpStatus.BAD_REQUEST, "Invalid JWT signature, 유효하지 않는 JWT 서명 입니다."),
    TokenExpiredJwtException(HttpStatus.BAD_REQUEST, "Expired JWT token, 만료된 JWT token 입니다."),
    TokenUnsupportedJwtException(HttpStatus.BAD_REQUEST, "Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다."),
    TokenIllegalArgumentException(HttpStatus.BAD_REQUEST, "JWT claims is empty, 잘못된 JWT 토큰 입니다."),
    RefreshTokenValidException(HttpStatus.BAD_REQUEST, "refreshToken이 유효하지 않습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
