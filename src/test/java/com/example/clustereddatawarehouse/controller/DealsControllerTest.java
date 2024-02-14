package com.example.clustereddatawarehouse.controller;

import com.example.clustereddatawarehouse.dto.ApiResponse;
import com.example.clustereddatawarehouse.dto.DealRequestDto;
import com.example.clustereddatawarehouse.dto.DealResponseDto;
import com.example.clustereddatawarehouse.service.DealsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
@WebMvcTest(DealsController.class)
class DealsControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private DealsService dealsService;

    @Test
    public void testSaveDeal() throws Exception {
        DealRequestDto requestDto = new DealRequestDto();
        DealResponseDto mockResponse = new DealResponseDto();
        when(dealsService.saveDeal(any(DealRequestDto.class))).thenReturn(mockResponse);

        MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/api/deals/create")
                        .content(asJsonString(requestDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        MockHttpServletResponse response = result.getResponse();
        ApiResponse apiResponse = new ObjectMapper().readValue(response.getContentAsString(), ApiResponse.class);


        verify(dealsService, times(1)).saveDeal(any(DealRequestDto.class));
    }


    @Test
    public void testGetOneDeal() throws Exception {
        String ID_TO_FIND = "kau8-2879-99ij";
        DealResponseDto responseDto = DealResponseDto.builder()
                .dealUniqueId(ID_TO_FIND)
                .toCurrencyISO("NGN")
                .orderingCurrencyISO("EUR")
                .amountInOrderingCurrency(new BigDecimal("10000.0"))
                .build();
        when(dealsService.getDeal(ID_TO_FIND))
                .thenReturn(responseDto);
        mvc.perform(get("/api/deals/" + ID_TO_FIND))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.errorList").doesNotExist())
                .andExpect(jsonPath("$.data.dealUniqueId").value(ID_TO_FIND));
    }

    @Test
    public void testGetAllFXDeals() throws Exception {
        Page<DealResponseDto> mockDealsPage = new PageImpl<>(Collections.emptyList());
        when(dealsService.getAllDeals(0, 10)).thenReturn(mockDealsPage);
        mvc.perform(MockMvcRequestBuilders.get("/api/deals")
                        .param("page", "0")
                        .param("size", "10")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        verify(dealsService, times(1)).getAllDeals(0, 10);
    }

    private String asJsonString(DealRequestDto deal) {
        try {
            return new ObjectMapper().writeValueAsString(deal);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}