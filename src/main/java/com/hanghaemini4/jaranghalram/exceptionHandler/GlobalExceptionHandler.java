package com.hanghaemini4.jaranghalram.exceptionHandler;

import com.hanghaemini4.jaranghalram.dto.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends RuntimeException{

//  <?>대신 <String> 확인하고 사용
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDto<String>> handlerException(Exception e){
        return new ResponseEntity<>(ResponseDto.fail(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
//    <String>일 경우 체크
    @ExceptionHandler(CommentException.class)
    public ResponseEntity<String> CommentException(Exception e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.TOO_EARLY);
    }
    @ExceptionHandler({PostLikeServiceException.class})
    public ResponseEntity<String> PostLikeServiceException(Exception e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.TOO_EARLY);
    }
    @ExceptionHandler(PostServiceException.class)
    public ResponseEntity<String> PostServiceException(Exception e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.TOO_EARLY);
    }
    @ExceptionHandler(UserServiceException.class)
    public ResponseEntity<String> UserServiceException(Exception e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.TOO_EARLY);
    }
    @ExceptionHandler(JwtUtilException.class)
    public ResponseEntity<String> JwtUtilException(Exception e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.TOO_EARLY);
    }
}
