package com.example.clustereddatawarehouse.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class DealResponseDto {
    private String dealUniqueId;

    private String orderingCurrencyISO;

    private String toCurrencyISO;

    private Instant dealTimestamp;

    private BigDecimal amountInOrderingCurrency;


}
