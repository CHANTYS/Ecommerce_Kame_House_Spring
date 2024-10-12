package com.KameHouse.ecom.entity;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class CartItemsProductsKey implements Serializable {
    private Long cartItemsId;

    public Long getCartItemsId() {
        return cartItemsId;
    }

    private Long productId;

    public Long getProductId() {
        return productId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartItemsProductsKey that = (CartItemsProductsKey) o;
        return Objects.equals(cartItemsId, that.cartItemsId) &&
                Objects.equals(productId, that.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cartItemsId, productId);
    }
}
