package com.example.clustereddatawarehouse.controller;


import com.example.clustereddatawarehouse.dto.ApiResponse;
import com.example.clustereddatawarehouse.dto.ResponseBuilder;
import com.example.clustereddatawarehouse.dto.DealRequestDto;
import com.example.clustereddatawarehouse.dto.DealResponseDto;
import com.example.clustereddatawarehouse.service.DealsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/deals")
@Slf4j
@Api(tags = "Deals API", description = "Operations related to FX deals")
public class DealsController {

    private final DealsService dealsService;

    public DealsController(DealsService dealsService) {
        this.dealsService = dealsService;
    }

    @PostMapping("/create")
    @ApiOperation(value = "Create a new FX deal")
    public ResponseEntity<ApiResponse> saveDeal(@RequestBody DealRequestDto requestDto) {
        DealResponseDto dealResponseDto = dealsService.saveDeal(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseBuilder.buildSuccessResponse(dealResponseDto));
    }

    @GetMapping("/{dealId}")
    @ApiOperation(value = "Get details of an FX deal by dealId")
    public ResponseEntity<ApiResponse> getDeal(@PathVariable String dealId) {
        DealResponseDto deal = dealsService.getDeal(dealId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseBuilder.buildSuccessResponse(deal));
    }


    @GetMapping
    @ApiOperation(value = "Get details of all  FX deal")
    public ResponseEntity<ApiResponse> getAllFXDeals(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<DealResponseDto> dealsPage = dealsService.getAllDeals(page, size);

        ApiResponse response = ApiResponse.builder()
                .message("FX deals retrieved successfully")
                .data(dealsPage.getContent())
                .status(HttpStatus.OK)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
