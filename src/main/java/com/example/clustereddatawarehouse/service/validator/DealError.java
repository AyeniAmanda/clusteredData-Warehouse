package com.example.clustereddatawarehouse.service.validator;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class DealError {
    private String message;
    private String field;
}
