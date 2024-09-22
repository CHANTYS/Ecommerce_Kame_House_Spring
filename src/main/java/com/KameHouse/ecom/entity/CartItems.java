package com.KameHouse.ecom.entity;

import com.KameHouse.ecom.dto.CartItemsDto;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static org.hibernate.annotations.OnDeleteAction.CASCADE;

@Entity
@Data
public class CartItems {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private Long price;
    private Long quantity;

    @ManyToOne(fetch = LAZY, optional = false)
    @JoinColumn(name = "productId", nullable = false)
    @OnDelete(action = CASCADE)
    private Product product;

    @ManyToOne(fetch = LAZY, optional = false)
    @JoinColumn(name = "userId", nullable = false)
    @OnDelete(action = CASCADE)
    private User user;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "orderId")
    private Order order;

    public CartItemsDto getCartDto() {
        var cartItemDto = new CartItemsDto();
        cartItemDto.setId(getId());
        cartItemDto.setPrice(getPrice());
        cartItemDto.setProductId(product.getId());
        cartItemDto.setQuantity(getQuantity());
        cartItemDto.setUserId(user.getId());
        cartItemDto.setProductName(product.getName());
        cartItemDto.setReturnedImg(product.getImg());

        return cartItemDto;
    }
}
