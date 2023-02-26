package com.hanghaemini4.jaranghalram.dto;

import com.hanghaemini4.jaranghalram.exceptionHandler.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

@Builder
@Getter
@AllArgsConstructor
public class ErrorResponse {
//    private int status;
    private String msg;

    public static ResponseEntity<ErrorResponse> error(ErrorCode errorCode) {
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(ErrorResponse.builder()
//                        .status(errorCode.getHttpStatus().value())
                        .msg(errorCode.getMessage())
                        .build());
    }
}
