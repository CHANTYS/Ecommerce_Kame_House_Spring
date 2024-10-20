package com.KameHouse.ecom.entity;

import com.KameHouse.ecom.dto.GetCartItemDto;
import com.KameHouse.ecom.dto.GetProductDto;
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

    public GetCartItemDto GetCartItemDto() {
        GetCartItemDto dto = new GetCartItemDto();
        dto.setId(cartItems.getId());
        dto.setQuantity(cartItems.getQuantity());
        dto.setTotalAmount(cartItems.getTotalAmount());
        dto.setAmount(cartItems.getAmount());
        dto.setDiscount(cartItems.getDiscount());

        cartItems.getCartItemsProducts().forEach(item -> {
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
