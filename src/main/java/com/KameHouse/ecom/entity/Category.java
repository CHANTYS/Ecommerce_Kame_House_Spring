package com.KameHouse.ecom.entity;

import com.KameHouse.ecom.dto.CategoryDto;
import com.KameHouse.ecom.dto.ProductDto;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "category")
@Data
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Lob
    private String description;

    public CategoryDto getDto() {
        CategoryDto dto = new CategoryDto();
        dto.setId(id);
        dto.setName(name);
        dto.setDescription(description);

        return dto;
    }
}
