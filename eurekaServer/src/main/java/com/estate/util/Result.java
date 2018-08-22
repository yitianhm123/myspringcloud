package com.estate.util;

import lombok.Data;

@Data
public class Result<T> {

    private T data;
    private String message;
    private String code;
}
