package com.myapp.restapi.researchconference.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomErrorHandling  {
    @ExceptionHandler
    public final ResponseEntity<ExceptionResponse> handleUserNotFoundException(InvalidUsernameOrPassword ex) {
        ExceptionResponse errorResponse = new ExceptionResponse(ex.getMessage());
        errorResponse.setStatus(HttpStatus.NOT_FOUND.value());
        errorResponse.setTimeStamp(System.currentTimeMillis());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public final ResponseEntity<ExceptionResponse> PrivilegesUsers(PrivilegesUserException ex) {
        ExceptionResponse errorResponse = new ExceptionResponse(ex.getMessage());
        errorResponse.setStatus(HttpStatus.FORBIDDEN.value());
        errorResponse.setTimeStamp(System.currentTimeMillis());
        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler
    public final ResponseEntity<ExceptionResponse> PrivilegesUsers(IllegalAccessException ex) {
        ExceptionResponse errorResponse = new ExceptionResponse(ex.getMessage());
        errorResponse.setStatus(HttpStatus.FORBIDDEN.value());
        errorResponse.setTimeStamp(System.currentTimeMillis());
        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler
    public final ResponseEntity<ExceptionResponse> UsernameExistedException(UsernameExistedException ex) {
        ExceptionResponse errorResponse = new ExceptionResponse(ex.getMessage());
        errorResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorResponse.setTimeStamp(System.currentTimeMillis());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    public final ResponseEntity<ExceptionResponse> PrivilegesUsers(NoDataFoundException ex) {
        ExceptionResponse errorResponse = new ExceptionResponse(ex.getMessage());
        errorResponse.setStatus(HttpStatus.NOT_FOUND.value());
        errorResponse.setTimeStamp(System.currentTimeMillis());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ExceptionResponse> handleAllExceptions(Exception ex) {
        ExceptionResponse errorResponse = new ExceptionResponse(ex.getMessage());
        errorResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorResponse.setTimeStamp(System.currentTimeMillis());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
