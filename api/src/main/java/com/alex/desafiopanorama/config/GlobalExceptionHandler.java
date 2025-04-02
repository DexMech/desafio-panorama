package com.alex.desafiopanorama.config;

import com.alex.desafiopanorama.dto.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorMessage> handleRuntime(RuntimeException ex) {
        if ("Order not found".equals(ex.getMessage())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ErrorMessage(ex.getMessage(),
                            java.time.ZonedDateTime.now(ZoneOffset.UTC)
                                    .format(DateTimeFormatter.ISO_OFFSET_DATE_TIME),
                            HttpStatus.NOT_FOUND.value()));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ErrorMessage(ex.getMessage(),
                        java.time.ZonedDateTime.now(ZoneOffset.UTC)
                                .format(DateTimeFormatter.ISO_OFFSET_DATE_TIME),
                HttpStatus.INTERNAL_SERVER_ERROR.value()));
    }
}