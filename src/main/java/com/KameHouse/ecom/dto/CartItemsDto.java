package com.KameHouse.ecom.dto;

import lombok.Data;

import java.util.List;

@Data
public class CartItemsDto {

    private Long id;

    private Long price;

    private Long quantity;

    private Long userId;

    private List<ProductDto> productDtos;
}
