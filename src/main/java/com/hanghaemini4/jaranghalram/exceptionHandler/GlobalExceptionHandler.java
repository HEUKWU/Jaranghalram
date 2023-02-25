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
    public ResponseEntity<ResponseDto<String>> handlerExcepiton(Exception e){
        return new ResponseEntity<>(ResponseDto.fail(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
//    <String>일 경우 체크
    @ExceptionHandler(CommentException.class)
    public ResponseEntity<ResponseDto<String>> CommentException(Exception e) {
        return new ResponseEntity<>(ResponseDto.fail(e.getMessage()), HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler({PostLikeServiceException.class})
    public ResponseEntity<ResponseDto<String>> PostLikeServiceException(Exception e) {
        return new ResponseEntity<>(ResponseDto.fail(e.getMessage()), HttpStatus.PAYMENT_REQUIRED);
    }
    @ExceptionHandler(PostServiceException.class)
    public ResponseEntity<ResponseDto<String>> PostServiceException(Exception e) {
        return new ResponseEntity<>(ResponseDto.fail(e.getMessage()), HttpStatus.FORBIDDEN);
    }
    @ExceptionHandler(UserServiceException.class)
    public ResponseEntity<ResponseDto<String>> UserServiceException(Exception e) {
        return new ResponseEntity<>(ResponseDto.fail(e.getMessage()), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(JwtUtilException.class)
    public ResponseEntity<ResponseDto<String>> JwtUtilException(Exception e) {
        return new ResponseEntity<>(ResponseDto.fail(e.getMessage()), HttpStatus.METHOD_NOT_ALLOWED);
    }
}
