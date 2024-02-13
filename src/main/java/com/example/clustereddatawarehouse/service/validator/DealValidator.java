package com.example.clustereddatawarehouse.service.validator;


import com.example.clustereddatawarehouse.dto.DealRequestDto;
import com.example.clustereddatawarehouse.model.Deal;
import com.example.clustereddatawarehouse.repository.DealsRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;

@Component
@RequestScope
public class DealValidator {

    private static final String NULL_OR_BLANK_MESSAGE = " cannot be null or blank";
    private static final String SHOULD_NOT_BE_SAME_MESSAGE = " should not be the same as ";


    private final DealsRepository dealsRepository;
    List<DealError> errors;

    public DealValidator(DealsRepository dealsRepository, List<DealError> errors) {
        this.dealsRepository = dealsRepository;
        this.errors = errors;
    }



    public List<DealError> validateRequestFields(DealRequestDto dealRequestDto) {
        errors.clear();

        validateNotNullOrBlank(dealRequestDto.getDealUniqueId(), "dealUniqueId");
        validateCurrency(dealRequestDto.getOrderingCurrencyISO(), "orderingCurrencyISO");
        validateCurrency(dealRequestDto.getToCurrencyISO(), "toCurrencyISO");
        validateDealTimestamp(dealRequestDto.getDealTimestamp());
        validateAmountInOrderingCurrency(dealRequestDto.getAmountInOrderingCurrency());
        validateOrderingAndToCurrency(dealRequestDto.getOrderingCurrencyISO(), dealRequestDto.getToCurrencyISO());

        return errors;
    }


    private void validateNotNullOrBlank(String value, String fieldName) {
        if (Objects.isNull(value) || value.isBlank()) {
            addError(fieldName + NULL_OR_BLANK_MESSAGE, fieldName);
        }
    }

    public List<DealError> validateDealExistence(String dealUniqueId) {
        errors.clear();

        validateNotNullOrBlank(dealUniqueId, "dealUniqueId");
        validateDealExistenceInRepository(dealUniqueId);

        return errors;
    }

    private void validateUniqueId(String dealUniqueId) {
        if (Objects.isNull(dealUniqueId) || dealUniqueId.isBlank()){
            addError("deal unique id cannot be null", "dealUniqueId");
        }
    }

    private void validateCurrency(String currency, String fieldName) {
        if (Objects.isNull(currency) || !isValidCurrencyCode(currency)) {
            addError("Invalid " + fieldName + NULL_OR_BLANK_MESSAGE, fieldName);
        }
    }

    private void validateDealTimestamp(Instant dealTimestamp) {
        if (Objects.isNull(dealTimestamp) || dealTimestamp.isAfter(Instant.now())) {
            addError("Invalid deal timestamp, deal timestamp" + NULL_OR_BLANK_MESSAGE +
                    " or after current date", "dealTimestamp");
        }
    }

    private void validateAmountInOrderingCurrency(BigDecimal amountInOrderingCurrency) {
        if (Objects.isNull(amountInOrderingCurrency) ||
                amountInOrderingCurrency.compareTo(BigDecimal.ZERO) <= 0) {
            addError("Invalid amount, amount should be greater than zero", "amountInOrderingCurrency");
        }
    }

    private void validateOrderingAndToCurrency(String orderingCurrencyISO, String toCurrencyISO) {
        if (orderingCurrencyISO.equals(toCurrencyISO)) {
            addError("Ordering currency" + SHOULD_NOT_BE_SAME_MESSAGE + "to currency", "");
        }
    }

    private boolean isValidCurrencyCode(String currencyCode) {
        return Currency.getAvailableCurrencies().stream()
                .noneMatch(currency -> currency.getCurrencyCode().equals(currencyCode));
    }

    private void validateDealExistenceInRepository(String dealUniqueId) {
        Optional<Deal> existingDeal = dealsRepository.findByDealUniqueId(dealUniqueId);
        if (existingDeal.isPresent()) {
            addError("Deal with id " + dealUniqueId + " already exists", "");
        }
    }

    private void addError(String message, String field) {
        DealError error = new DealError(message, field);
        errors.add(error);
    }
}

