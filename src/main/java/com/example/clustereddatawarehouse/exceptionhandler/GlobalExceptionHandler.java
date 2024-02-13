package com.example.clustereddatawarehouse.exceptionhandler;


import com.example.clustereddatawarehouse.dto.ApiResponse;
import com.example.clustereddatawarehouse.exceptions.InvalidRequestException;
import com.example.clustereddatawarehouse.exceptions.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<Object> handleInvalidException(InvalidRequestException ex) {
        log.error("InvalidRequestException: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.builder()
                .message(ex.getMessage())
                .status(HttpStatus.BAD_REQUEST)
                .build()
        );
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(ResourceNotFoundException ex) {
        log.error("ResourceNotFoundException: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.builder()
                .message(ex.getMessage())
                .status(HttpStatus.NOT_FOUND)
                .build()
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGlobalException(Exception ex) {
        log.error("Global Exception: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.builder()
                .message(ex.getMessage())
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .build()
        );
    }

}

