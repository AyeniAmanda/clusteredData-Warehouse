package com.example.clustereddatawarehouse.dto;

import com.example.clustereddatawarehouse.service.validator.DealError;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.util.List;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse {
    private String message;
    private HttpStatus status;
    private Object data;
    private List<DealError> errorList;
    private String error;

    public static ApiResponse success(Object data) {
        return ApiResponse.builder()
                .message("successful")
                .data(data)
                .status(HttpStatus.OK)
                .build();
    }

    public static ApiResponse error(List<DealError> errors, String message, HttpStatus status) {
        return ApiResponse.builder()
                .message("error")
                .status(status)
                .errorList(errors)
                .error(message)
                .build();
    }
}
