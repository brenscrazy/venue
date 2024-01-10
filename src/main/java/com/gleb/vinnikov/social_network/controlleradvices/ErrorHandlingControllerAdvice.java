package com.gleb.vinnikov.social_network.controlleradvices;

import jakarta.validation.ConstraintViolationException;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;

import java.text.Format;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class ErrorHandlingControllerAdvice {

    @ExceptionHandler({IllegalArgumentException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleConstraintViolation(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(new ErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleMissingParams(MethodArgumentNotValidException ex) {
        for (ObjectError error : ex.getAllErrors()) {
            return ResponseEntity.badRequest().body(new ErrorResponse(error.getDefaultMessage()));
        }
        return null;
    }

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> handleNoSuchElementException(NoSuchElementException ex) {
        return ResponseEntity.notFound().build();
    }

    @Data
    public static class ErrorResponse {
        private final String errorMessage;
    }

}
