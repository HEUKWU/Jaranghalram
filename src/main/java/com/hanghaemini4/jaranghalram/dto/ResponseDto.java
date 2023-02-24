package com.hanghaemini4.jaranghalram.dto;

public class ResponseDto<T> {

    private T result;


    public ResponseDto (T data) {
        this.result = data;
    }

    public static <T> ResponseDto<T> success(T result) {
        return new ResponseDto<>(result);
    }

    public static <T> ResponseDto<T> fail(T result) {
        return new ResponseDto<>(result);
    }

}
