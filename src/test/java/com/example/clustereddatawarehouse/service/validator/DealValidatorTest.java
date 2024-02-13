package com.example.clustereddatawarehouse.service.validator;


import com.example.clustereddatawarehouse.dto.DealRequestDto;
import com.example.clustereddatawarehouse.model.Deal;
import com.example.clustereddatawarehouse.repository.DealsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
@DisplayName("DealValidator Tests")
class DealValidatorTest {


    @Mock
    DealsRepository dealsRepository;

    DealValidator validator;

    List<DealError> errors;

    @BeforeEach
    void setUp() {
        errors = new ArrayList<>();
        validator = new DealValidator(dealsRepository, errors);
    }

    @Test
    @DisplayName("Validating Deal Existence - Deal Does Not Exist")
    void testValidateDealExistenceNotExists() {
        DealRequestDto dealRequest = createFXDealRequest();
        lenient().when(dealsRepository.findByDealUniqueId(dealRequest.getDealUniqueId()))
                .thenReturn(Optional.empty());
        List<DealError> dealExistence = validator.validateDealExistence(dealRequest.getDealUniqueId());
        assertTrue(dealExistence.isEmpty(), "No errors should be present for non-existing deal");
    }

    @Test
    @DisplayName("Validating Deal Existence - Deal Already Exists")
    void testValidateDealExistenceExists() {
        DealRequestDto dealRequest = createFXDealRequest();
        Deal deal = createDeal(dealRequest);
        lenient().when(dealsRepository.findByDealUniqueId(dealRequest.getDealUniqueId()))
                .thenReturn(Optional.of(deal));
        List<DealError> dealExistence = validator.validateDealExistence(dealRequest.getDealUniqueId());
        assertFalse(dealExistence.isEmpty(), "Errors should be present for existing deal");
    }


    @Test
    @DisplayName("Validating Request Fields - All Fields Valid")
    void testWhenRequestFieldsAreValid() {
        DealRequestDto dealRequestDto = createFXDealRequest();
        List<DealError> dealErrors = validator.validateRequestFields(dealRequestDto);
        assertEquals(2, dealErrors.size());
    }


    @Test
    @DisplayName("Validating Request Fields - One Field Invalid")
    void testValidateRequestFieldsOneInvalid() {
        DealRequestDto dealRequestDto = createFXDealRequest();
        dealRequestDto.setDealTimestamp(null);
        List<DealError> dealErrors = validator.validateRequestFields(dealRequestDto);
        assertFalse(dealErrors.isEmpty(), "Errors should be present for invalid request fields");
    }


    private Deal createDeal(DealRequestDto requestDto) {
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

    private DealRequestDto createFXDealRequest() {
        return DealRequestDto.builder()
                .dealUniqueId("id")
                .toCurrencyISO("NGN")
                .dealTimestamp(Instant.now().minusSeconds(15))
                .orderingCurrencyISO("EUR")
                .amountInOrderingCurrency(new BigDecimal("10000.0"))
                .build();
    }
}