package com.KameHouse.ecom.dto;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class GetCartItemDto {
    private Long id;

    private Double totalAmount;

    private Long quantity;

    private String couponName;

    private Double discount;

    private Double amount;

    private Set<GetProductDto> productDtos = new HashSet<>();
}
