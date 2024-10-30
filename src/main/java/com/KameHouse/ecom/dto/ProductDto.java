package com.KameHouse.ecom.dto;


import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ProductDto {

    private Long id;

    private String name;

    private Double price;

    private String description;

    private MultipartFile img;

    private byte[] returnedImg;
    private String fileName;

    private Long categoryId;

    private String categoryName;

}
