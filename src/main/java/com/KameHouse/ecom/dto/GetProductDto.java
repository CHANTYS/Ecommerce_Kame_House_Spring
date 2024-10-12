package com.KameHouse.ecom.dto;

import lombok.Data;

@Data
public class GetProductDto {
    private Long id;

    private byte[] returnedImg;

    private Long quantity;

    private String productName;

    private Double price;
}
