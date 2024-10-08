package com.KameHouse.ecom.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderedProductsResponseDto {

    private List<OrderedProductDetails> orderedProductDetailsList;

    private Long orderAmount;

}
