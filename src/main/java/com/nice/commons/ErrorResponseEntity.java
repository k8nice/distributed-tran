package com.nice.commons;

import lombok.Data;

/**
 * @author ningh
 */
@Data
public class ErrorResponseEntity {
    private int code;
    private String message;

    public ErrorResponseEntity() {
    }

    public ErrorResponseEntity(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
