package com.myapp.restapi.researchconference.Exception;

import lombok.Data;

@Data
public class PrivilegesUserException extends RuntimeException{
    public PrivilegesUserException(String message) {
        super(message);
    }
}
