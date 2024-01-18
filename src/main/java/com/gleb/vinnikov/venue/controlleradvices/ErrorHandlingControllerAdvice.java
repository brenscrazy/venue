package com.gleb.vinnikov.venue.controlleradvices;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.time.DateTimeException;
import java.util.NoSuchElementException;

@RestControllerAdvice
@Slf4j
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

    @ExceptionHandler({InternalAuthenticationServiceException.class, BadCredentialsException.class})
    public ResponseEntity<String> handleBadCredentialsException(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Bad credentials. Please check your username and password");// TODO: 08.06.2023 add message to properties
    }

    @ExceptionHandler({DateTimeException.class})
    public ResponseEntity<ErrorResponse> handleWrongDate(Throwable ex) {
        return ResponseEntity.badRequest().body(new ErrorResponse("Provided date is wrong. Check if it is correct"));
    }

    @ExceptionHandler({Throwable.class})
    public ResponseEntity<ErrorResponse> unhandledException(Throwable ex) {
        log.error("Uncaught", ex);
        return ResponseEntity.internalServerError().body(new ErrorResponse("Unknown error happened, try again later"));
    }

    @Data
    public static class ErrorResponse {
        private final String errorMessage;
    }

}
