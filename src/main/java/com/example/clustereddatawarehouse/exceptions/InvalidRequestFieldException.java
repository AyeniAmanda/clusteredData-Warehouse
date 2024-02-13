package com.example.clustereddatawarehouse.exceptions;


import com.example.clustereddatawarehouse.service.validator.DealError;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Setter
@Getter
public class InvalidRequestFieldException extends RuntimeException {
    private final List<DealError> errors;

    public InvalidRequestFieldException(String message, List<DealError> errors) {
        super(message);
        this.errors = errors;
    }
}
