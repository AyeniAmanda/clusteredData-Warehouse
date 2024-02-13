package com.example.clustereddatawarehouse.service.mapper;


import com.example.clustereddatawarehouse.dto.DealRequestDto;
import com.example.clustereddatawarehouse.dto.DealResponseDto;
import com.example.clustereddatawarehouse.model.Deal;
import org.springframework.stereotype.Component;

import java.util.Currency;

@Component
public class DealMapperImpl implements DealMapper {
    @Override
    public DealResponseDto mapToDealResponseDto(Deal deal) {
        return DealResponseDto.builder()
                .dealUniqueId(deal.getDealUniqueId())
                .dealTimestamp(deal.getDealTimestamp())
                .orderingCurrencyISO(deal.getOrderingCurrencyISO().getCurrencyCode())
                .toCurrencyISO(deal.getToCurrencyISO().getCurrencyCode())
                .amountInOrderingCurrency(deal.getAmountInOrderingCurrency())
                .build();
    }

    @Override
    public Deal mapToDealEntity(DealRequestDto dealRequestDto) {
        Currency orderingCurrency = Currency.getInstance(dealRequestDto.getOrderingCurrencyISO());
        Currency toCurrency = Currency.getInstance(dealRequestDto.getToCurrencyISO());

        return Deal.builder()
                .dealUniqueId(dealRequestDto.getDealUniqueId())
                .orderingCurrencyISO(orderingCurrency)
                .toCurrencyISO(toCurrency)
                .dealTimestamp(dealRequestDto.getDealTimestamp())
                .amountInOrderingCurrency(dealRequestDto.getAmountInOrderingCurrency())
                .build();
    }
}
