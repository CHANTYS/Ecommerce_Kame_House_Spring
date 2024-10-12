package com.KameHouse.ecom.entity;


import com.KameHouse.ecom.dto.CartItemsDto;
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
}
