package com.example.clustereddatawarehouse.service.serviceImpl;


import com.example.clustereddatawarehouse.dto.DealRequestDto;
import com.example.clustereddatawarehouse.dto.DealResponseDto;
import com.example.clustereddatawarehouse.exceptions.InvalidRequestException;
import com.example.clustereddatawarehouse.exceptions.ResourceNotFoundException;
import com.example.clustereddatawarehouse.model.Deal;
import com.example.clustereddatawarehouse.repository.DealsRepository;
import com.example.clustereddatawarehouse.service.DealsService;
import com.example.clustereddatawarehouse.service.mapper.DealMapper;
import com.example.clustereddatawarehouse.service.validator.DealError;
import com.example.clustereddatawarehouse.service.validator.DealValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class DealsServiceImpl implements DealsService {

    private final DealsRepository dealsRepository;

    private final DealMapper dealMapper;

    private final DealValidator validator;

    public DealsServiceImpl(DealsRepository dealsRepository, DealMapper dealMapper, DealValidator validator) {
        this.dealsRepository = dealsRepository;
        this.dealMapper = dealMapper;
        this.validator = validator;
    }

    @Override
    public DealResponseDto saveDeal(DealRequestDto dealRequestDto) throws InvalidRequestException {

        List<DealError> requestFields = validator.validateRequestFields(dealRequestDto);
        List<DealError> dealExistence = validator.validateDealExistence(dealRequestDto.getDealUniqueId());

        if (!requestFields.isEmpty() || !dealExistence.isEmpty()) {
            throw new InvalidRequestException("Invalid Request fields or Deal already exists",
                    Stream.concat(requestFields.stream(), dealExistence.stream()).collect(Collectors.toList()));
        }

        Deal deal = dealsRepository.save(dealMapper.mapToDealEntity(dealRequestDto));
        return dealMapper.mapToDealResponseDto(deal);
    }


    @Override
    public DealResponseDto getDeal(String dealUniqueId) {
        Deal deal = dealsRepository.findByDealUniqueId(dealUniqueId)
                .orElseThrow(() -> new ResourceNotFoundException("Deal with id " + dealUniqueId + " does not exist"));
        return dealMapper.mapToDealResponseDto(deal);
    }


    @Override
    public Page<DealResponseDto> getAllDeals(int page, int size) {
        log.debug("All deals - page: {}, size: {}", page, size);
        return dealsRepository.findAll(PageRequest.of(page, size))
                .map(dealMapper::mapToDealResponseDto);
    }

}
