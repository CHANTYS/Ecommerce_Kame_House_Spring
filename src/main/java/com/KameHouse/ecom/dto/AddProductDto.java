package com.KameHouse.ecom.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class AddProductDto {
-
    private Long id;

    private String name;

    private String rating;

    private Long availableQuantity;

    private Double price;

    private String description;

    private MultipartFile img;

    private byte[] returnedImg;

    private String categoryId;

}
