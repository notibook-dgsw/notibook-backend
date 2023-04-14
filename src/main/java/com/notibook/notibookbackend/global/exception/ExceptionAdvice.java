package com.notibook.notibookbackend.global.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ExceptionAdvice {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public ResponseEntity<ErrorResponse> onInputException(BindException exception) {
        String message = exception.getBindingResult().getFieldErrors().stream()
                .map(field -> String.format("(%s) %s", field.getField(), field.getDefaultMessage()))
                .collect(Collectors.joining(", "));

        return new ResponseEntity<>(ErrorResponse.builder()
                .message(message)
                .time(LocalDateTime.now().toString())
                .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> onBusinessException(BusinessException exception) {
        return new ResponseEntity<>(ErrorResponse.builder()
                .message(exception.getMessage())
                .time(LocalDateTime.now().toString())
                .build(), HttpStatus.valueOf(exception.getStatus()));
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> onUncaughtException(RuntimeException exception) {
        exception.printStackTrace();

        return new ResponseEntity<>(ErrorResponse.builder()
                .message(exception.getMessage())
                .time(LocalDateTime.now().toString())
                .build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
