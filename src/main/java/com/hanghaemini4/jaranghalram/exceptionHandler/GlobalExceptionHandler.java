package com.hanghaemini4.jaranghalram.exceptionHandler;

import com.hanghaemini4.jaranghalram.dto.ResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ResponseDto.Error> handlerException(CustomException e) {
        return ResponseEntity.status(e.getErrorCode().getHttpStatus())
                .body(new ResponseDto.Error(e.getErrorCode().getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseDto.Error> validationException() {
        return ResponseEntity.status(ErrorCode.InValidException.getHttpStatus())
                .body(new ResponseDto.Error(ErrorCode.InValidException.getMessage()));
    }
}
