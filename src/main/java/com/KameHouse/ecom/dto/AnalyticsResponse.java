package com.KameHouse.ecom.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;


@Data
@AllArgsConstructor
public class AnalyticsResponse {

    private Long currentMonthOrders;
    private Long previousMonthOrders;
    private Double currentMonthEarnings;
    private Double previousMonthEarnings;

    private Long placed;
    private Long shipped;
    private Long delivered;
}
