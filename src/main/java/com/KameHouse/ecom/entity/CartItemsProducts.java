package com.KameHouse.ecom.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "cart_items_products")
public class CartItemsProducts {
    @EmbeddedId
    private CartItemsProductsKey cartItemsProductsKey = new CartItemsProductsKey();

    public com.KameHouse.ecom.entity.CartItemsProductsKey getCartItemsProductsKey() {
        return cartItemsProductsKey;
    }

    @ManyToOne
    @MapsId("cartItemsId")
    @JoinColumn(name = "cart_items_id")
    private CartItems cartItems;

    public CartItems getCartItems() {
        return cartItems;
    }

    public void setCartItems(CartItems cartItems) {
        this.cartItems = cartItems;
    }

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    private Product product;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    private Long quantity;

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }
}
