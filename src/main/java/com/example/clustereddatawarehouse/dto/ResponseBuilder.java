package com.example.clustereddatawarehouse.dto;

import com.example.clustereddatawarehouse.service.validator.DealError;
import org.springframework.http.HttpStatus;

import java.util.List;


public class ResponseBuilder {

    public static ApiResponse buildSuccessResponse(Object data) {
        return ApiResponse.success(data);
    }

    public static ApiResponse buildErrorResponse(List<DealError> errors, String message, HttpStatus status) {
        return ApiResponse.error(errors, message, status);
    }
}
