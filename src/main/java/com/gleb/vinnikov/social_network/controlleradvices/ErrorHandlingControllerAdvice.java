package com.gleb.vinnikov.social_network.controlleradvices;

import jakarta.validation.ConstraintViolationException;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.text.Format;

@RestControllerAdvice
public class ErrorHandlingControllerAdvice {

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<ErrorResponse> handleConstraintViolation(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(new ErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMissingParams(MethodArgumentNotValidException ex) {
        for (ObjectError error : ex.getAllErrors()) {
            return ResponseEntity.badRequest().body(new ErrorResponse(error.getDefaultMessage()));
        }
        return null;
    }

    @Data
    public static class ErrorResponse {
        private final String errorMessage;
    }

}
