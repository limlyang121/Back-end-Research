package com.myapp.restapi.researchconference.Exception;

import lombok.Data;

@Data
public class ExceptionResponse {
    private int status;
    private String message;
    private long timeStamp;

    public ExceptionResponse(String message) {
        this.message = message;
    }

}
