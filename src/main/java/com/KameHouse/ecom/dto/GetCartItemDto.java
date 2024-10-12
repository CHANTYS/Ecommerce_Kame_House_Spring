package com.KameHouse.ecom.dto;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class GetCartItemDto {
    private Long id;

    private Double price;

    private Long quantity;

    private Set<GetProductDto> productDtos = new HashSet<>();
}
