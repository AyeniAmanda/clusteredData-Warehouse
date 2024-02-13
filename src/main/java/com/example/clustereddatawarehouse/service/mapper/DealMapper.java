package com.example.clustereddatawarehouse.service.mapper;


import com.example.clustereddatawarehouse.dto.DealRequestDto;
import com.example.clustereddatawarehouse.dto.DealResponseDto;
import com.example.clustereddatawarehouse.model.Deal;

public interface DealMapper {
    DealResponseDto mapToDealResponseDto(Deal deal);

    Deal mapToDealEntity(DealRequestDto dealRequestDto);
}
