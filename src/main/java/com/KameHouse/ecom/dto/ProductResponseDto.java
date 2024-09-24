package com.KameHouse.ecom.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ProductResponseDto {
    private Long id;

    private String name;

    private Long price;


    private String description;


    private byte[] byteImg;

    private long categoryId;

    private MultipartFile img;
}
