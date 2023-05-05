package com.myapp.restapi.researchconference.Exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class InvalidUsernameOrPassword extends RuntimeException {
    public InvalidUsernameOrPassword(String message) {
        super(message);
    }
}
