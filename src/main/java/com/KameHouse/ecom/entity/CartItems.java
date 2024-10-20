package com.KameHouse.ecom.entity;

import com.KameHouse.ecom.dto.GetCartItemDto;
import com.KameHouse.ecom.dto.GetProductDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.*;

import java.util.HashSet;
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

    private Double totalAmount;

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
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

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "coupon_id", referencedColumnName = "id")
    private Coupon coupon;

    public Coupon getCoupon() {
        return coupon;
    }

    public void setCoupon(Coupon coupon) {
        this.coupon = coupon;
    }

    private Double discount;

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    private Double amount;

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public GetCartItemDto GetCartItemDto() {
        GetCartItemDto dto = new GetCartItemDto();
        dto.setId(id);
        dto.setQuantity(quantity);
        dto.setTotalAmount(totalAmount);
        dto.setAmount(amount);
        dto.setDiscount(discount);

        cartItemsProducts.forEach(item -> {
            GetProductDto productDto = new GetProductDto();
            productDto.setProductName(item.getProduct().getName());
            productDto.setReturnedImg(item.getProduct().getImg());
            productDto.setQuantity(item.getQuantity());
            productDto.setId(item.getProduct().getId());
            productDto.setPrice(item.getProduct().getPrice());
            dto.getProductDtos().add(productDto);
        });

        if(coupon != null) {
            dto.setCouponName(coupon.getName());
        }

        return dto;
    }
}
