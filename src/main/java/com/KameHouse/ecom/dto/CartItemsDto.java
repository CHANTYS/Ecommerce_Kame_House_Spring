package com.KameHouse.ecom.dto;

import com.KameHouse.ecom.entity.Product;
import lombok.Data;

import java.util.List;

@Data
public class CartItemsDto {

    private Long id;

    private Long price;

    private Long quantity;

    //private Long productId;

   // private Long orderId;

    //private String productName;

    //private byte[] returnedImg;

    private Long userId;

    private List<ProductDto> productDtos;


}
