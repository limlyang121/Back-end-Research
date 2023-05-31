package com.myapp.restapi.researchconference.Exception;

import lombok.Data;

@Data
public class UsernameExistedException extends RuntimeException{
    public UsernameExistedException(String message) {
        super(message);
    }
}
