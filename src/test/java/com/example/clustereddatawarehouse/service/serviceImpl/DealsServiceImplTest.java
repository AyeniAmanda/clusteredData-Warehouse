package com.example.clustereddatawarehouse.service.serviceImpl;


import com.example.clustereddatawarehouse.dto.DealRequestDto;
import com.example.clustereddatawarehouse.dto.DealResponseDto;
import com.example.clustereddatawarehouse.exceptions.InvalidRequestFieldException;
import com.example.clustereddatawarehouse.model.Deal;
import com.example.clustereddatawarehouse.repository.DealsRepository;
import com.example.clustereddatawarehouse.service.DealsService;
import com.example.clustereddatawarehouse.service.mapper.DealMapper;
import com.example.clustereddatawarehouse.service.validator.DealError;
import com.example.clustereddatawarehouse.service.validator.DealValidator;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
class DealsServiceImplTest {

    @Mock
    DealsRepository dealsRepository;

    @Mock
    DealMapper dealMapper;

    @Mock
    DealValidator validator;

    DealsService dealsService;
    @BeforeEach
    void setUp(){
        dealsService = new DealsServiceImpl(dealsRepository, dealMapper, validator);
    }

    @Test
    void saveDealWhenNoDealExist() {
        DealRequestDto requestDto = createDealRequest();
        Deal deal = createDeal(requestDto);
        lenient().when(dealsRepository.findByDealUniqueId(anyString()))
                .thenReturn(Optional.empty());
        lenient().when(dealsRepository.save(deal)).
                thenReturn(deal);
        lenient().when(dealMapper.mapToDealEntity(requestDto))
                        .thenReturn(deal);
        DealResponseDto responseDto = dealsService.saveDeal(requestDto);
        verify(validator, times(1)).validateRequestFields(any(DealRequestDto.class));
        verify(validator, times(1)).validateDealExistence(requestDto.getDealUniqueId());
        verify(dealsRepository, times(1)).save(deal);
    }

    @Test
    void throwsExceptionWhenOneOrMoreFieldsAreNull(){
        DealRequestDto dealRequestDto = DealRequestDto.builder()
                .dealTimestamp(Instant.now().minusSeconds(90))
                .build();
        List<DealError> errorList = new ArrayList<>();
        DealError error = new DealError();
        error.setErrorMessage("deal unique id should not be null");
        errorList.add(error);
        error = new DealError();
        error.setErrorMessage("to currency should not be null");
        errorList.add(error);

        error = new DealError();
        error.setErrorMessage("currency should not be null");
        errorList.add(error);

        lenient().when(validator.validateRequestFields(dealRequestDto))
                .thenReturn(errorList);
        InvalidRequestFieldException ex = assertThrows(InvalidRequestFieldException.class, () -> {
            dealsService.saveDeal(dealRequestDto);
        });
        assertEquals("Invalid Request fields",ex.getMessage());
    }

    @Test
    void testExceptionThrownWhenUniqueIdAlreadyExists(){
        DealRequestDto dealRequestDto = createDealRequest();
        Deal deal = createDeal(dealRequestDto);
        List<DealError> errorList = new ArrayList<>();
        DealError error = new DealError();
        error.setErrorMessage("Deal already ex");
        errorList.add(error);

        lenient().when(dealsRepository.findByDealUniqueId(dealRequestDto.getDealUniqueId()))
                        .thenReturn(Optional.of(deal));
        lenient().when(validator.validateDealExistence("id"))
                .thenReturn(errorList);

        InvalidRequestFieldException ex = assertThrows(InvalidRequestFieldException.class, () -> {
            dealsService.saveDeal(dealRequestDto);
        });
        assertEquals("Deal already exist",ex.getMessage());
    }

    @Test
    void testGetOneDeal(){
        DealRequestDto dealRequestDto = createDealRequest();
        Deal deal = createDeal(dealRequestDto);
        when(dealsRepository.findByDealUniqueId(anyString()))
                .thenReturn(Optional.of(deal));
        dealsService.getDeal(dealRequestDto.getDealUniqueId());
        verify(dealsRepository, times(1)).findByDealUniqueId(anyString());
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
}