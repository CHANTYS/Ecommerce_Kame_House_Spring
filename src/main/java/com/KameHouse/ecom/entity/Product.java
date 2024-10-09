package com.KameHouse.ecom.entity;

import com.KameHouse.ecom.dto.ProductDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;

@Entity
@Data
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Long price;

    @Lob
    @Column(name = "description")
    private String description;

    @Lob
    @Column(columnDefinition = "longblob")
    private byte[] img;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Category category;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "products")
    private List<Order> Orders;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "products")
    private List<CartItems> CartItems;

    public ProductDto getProductDto() {
        ProductDto productDto = new ProductDto();
        productDto.setId(id);
        productDto.setName(name);
        productDto.setPrice(price);
        productDto.setDescription(description);
        productDto.setReturnedImg(img);
        productDto.setCategoryId(category.getId());
        productDto.setCategoryName(category.getName());
        return productDto;
    }
}
