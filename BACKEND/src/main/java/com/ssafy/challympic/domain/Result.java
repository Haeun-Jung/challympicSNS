package com.ssafy.challympic.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Result<T> {
    private boolean isSuccess;
    private int code;
    private T data;

    public Result(boolean isSuccess, int code) {
        this.isSuccess = isSuccess;
        this.code = code;
    }
}
