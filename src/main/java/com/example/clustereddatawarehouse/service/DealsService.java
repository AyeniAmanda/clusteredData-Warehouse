package com.example.clustereddatawarehouse.service;


import com.example.clustereddatawarehouse.dto.DealRequestDto;
import com.example.clustereddatawarehouse.dto.DealResponseDto;
import com.example.clustereddatawarehouse.exceptions.InvalidRequestException;
import org.springframework.data.domain.Page;

public interface DealsService {
    DealResponseDto saveDeal(DealRequestDto dealRequestDto) throws InvalidRequestException;

    DealResponseDto getDeal(String dealUniqueId);

    Page<DealResponseDto> getAllDeals(int page, int size);


}
