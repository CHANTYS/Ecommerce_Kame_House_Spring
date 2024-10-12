package com.KameHouse.ecom.entity;


import com.KameHouse.ecom.dto.CartItemsDto;
import com.KameHouse.ecom.dto.GetCartItemDto;
import com.KameHouse.ecom.dto.GetProductDto;
import com.KameHouse.ecom.dto.ProductDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class CartItems {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private Double price;

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    private Long quantity;

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cartItems")
    @JsonIgnore
    private Set<CartItemsProducts> cartItemsProducts = new HashSet<>();

    public Set<CartItemsProducts> getCartItemsProducts() {
        return cartItemsProducts;
    }

    public void setCartItemsProducts(Set<CartItemsProducts> cartItemsProducts) {
        this.cartItemsProducts = cartItemsProducts;
    }

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    /* @OneToOne(fetch = FetchType.LAZY)
            @JoinColumn(name = "order_id")
            private Order order;
            */

    public GetCartItemDto GetCartItemDto() {
        GetCartItemDto dto = new GetCartItemDto();
        dto.setId(id);
        dto.setQuantity(quantity);
        dto.setPrice(price);

        cartItemsProducts.forEach(item -> {
            GetProductDto productDto = new GetProductDto();
            productDto.setProductName(item.getProduct().getName());
            productDto.setReturnedImg(item.getProduct().getImg());
            productDto.setQuantity(item.getQuantity());
            productDto.setId(item.getProduct().getId());
            productDto.setPrice(item.getProduct().getPrice());
            dto.getProductDtos().add(productDto);
        });

        return dto;
    }
}
