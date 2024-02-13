package com.example.clustereddatawarehouse.service.validator;


import com.example.clustereddatawarehouse.dto.DealRequestDto;
import com.example.clustereddatawarehouse.model.Deal;
import com.example.clustereddatawarehouse.repository.DealsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Currency;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
class DealValidatorTest {


    @Mock
    DealsRepository dealsRepository;

    DealValidator validator;

    List<DealError> errors;

    @BeforeEach
    void setUp(){
        validator = new DealValidator(dealsRepository,errors);
    }
    @Test
    void testWhenRequestFieldsAreValid() {
        DealRequestDto dealRequestDto = createDealRequest();
        List<DealError> dealErrors = validator.validateRequestFields(dealRequestDto);
        assertEquals(0, dealErrors.size());
    }

    @Test
    void testWhenOneRequestFieldIsInvalid(){
        DealRequestDto dealRequestDto = createDealRequest();
        dealRequestDto.setDealTimestamp(null);
        List<DealError> dealErrors = validator.validateRequestFields(dealRequestDto);
        assertTrue(dealErrors.size() > 0);
    }

    @Test
    void testDealExistenceWhenFalse() {
        DealRequestDto dealRequest = createDealRequest();
//        Deal deal = createDeal(dealRequest);
        lenient().when(dealsRepository.findByDealUniqueId(dealRequest.getDealUniqueId()))
                .thenReturn(Optional.empty());
        List<DealError> dealExistence = validator.validateDealExistence(dealRequest.getDealUniqueId());
        assertTrue(dealExistence.isEmpty());
    }


    @Test
    void testDealExistenceWhenTrue() {
        DealRequestDto dealRequest = createDealRequest();
        Deal deal = createDeal(dealRequest);
        lenient().when(dealsRepository.findByDealUniqueId(dealRequest.getDealUniqueId()))
                .thenReturn(Optional.of(deal));
        List<DealError> dealExistence = validator.validateDealExistence(dealRequest.getDealUniqueId());
        assertFalse(dealExistence.isEmpty());
    }

    private Deal createDeal(DealRequestDto requestDto){
        Currency toCurrency = Currency.getInstance(requestDto.getToCurrencyISO());
        Currency fromCurrency = Currency.getInstance(requestDto.getOrderingCurrencyISO());

        return Deal.builder()
                .dealTimestamp(requestDto.getDealTimestamp())
                .dealUniqueId(requestDto.getDealUniqueId())
                .toCurrencyISO(toCurrency)
                .orderingCurrencyISO(fromCurrency)
                .amountInOrderingCurrency(requestDto.getAmountInOrderingCurrency())
                .id(1L)
                .build();
    }
    private DealRequestDto createDealRequest() {
        return DealRequestDto.builder()
                .dealUniqueId("id")
                .toCurrencyISO("NGN")
                .dealTimestamp(Instant.now().minusSeconds(15))
                .orderingCurrencyISO("EUR")
                .amountInOrderingCurrency(new BigDecimal("10000.0"))
                .build();
    }
}