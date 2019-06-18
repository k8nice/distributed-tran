package com.nice.commons;

import lombok.Data;

@Data
public class CustomException extends RuntimeException {
    private int code;

    private String message;

    public CustomException(){
        super();
    }

    public CustomException(int code,String message){
        super(message);
        this.setCode(code);
    }

}
