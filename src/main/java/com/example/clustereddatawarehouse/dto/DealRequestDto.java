package com.example.clustereddatawarehouse.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class DealRequestDto {
    private String dealUniqueId;

    private String orderingCurrencyISO;

    private String toCurrencyISO;

    private Instant dealTimestamp;

    private BigDecimal amountInOrderingCurrency;
}
