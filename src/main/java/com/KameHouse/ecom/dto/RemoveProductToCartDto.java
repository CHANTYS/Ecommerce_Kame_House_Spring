package com.KameHouse.ecom.dto;

import lombok.Data;

@Data
public class RemoveProductToCartDto {
    private Long productId;

    private Long userId;
}
