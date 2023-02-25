package com.hanghaemini4.jaranghalram.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponseDto<T> {

    boolean success;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T result;


    public ResponseDto (T data) {
        this.result = data;
    }

    public static <T> ResponseDto<T> success(T result) {
        return new ResponseDto<>(true, result);
    }

    public static <T> ResponseDto<T> fail(T result) {
        return new ResponseDto<>(false, result);
    }

}
