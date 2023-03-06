package com.example.CLP_Case_Study.advice;

import com.example.CLP_Case_Study.exceptions.NotLoggedInException;
import com.example.CLP_Case_Study.exceptions.ResourceNotFoundException;
import com.example.CLP_Case_Study.exceptions.IllegalArgumentException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(NotLoggedInException.class)
    public ResponseEntity<Object> handleNotLoggedInException(HttpServletRequest request, NotLoggedInException notLoggedInException) {

        String errorMessage = "Must be logged in to perform this action";

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorMessage);
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(HttpServletRequest request, IllegalArgumentException illegalArgumentException) {

        String errorMessage = "Invalid argument passed to API";

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFoundException(HttpServletRequest request, ResourceNotFoundException resourceNotFoundException) {

        String errorMessage = "Requested resource not found";

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
    }
}