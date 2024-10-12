package com.KameHouse.ecom.service.customer.cart;

import com.KameHouse.ecom.dto.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CartService {


    ResponseEntity<?> addProductToCart(AddCartItemDto cartItemsDto);

    ResponseEntity<?>  getCartByUserId(Long userId);

    ResponseEntity<?> decreaseProductQuantity(QuantityChangeProductDto quantityChangeProductDto);

    ResponseEntity<?> increaseProductQuantity(QuantityChangeProductDto quantityChangeProductDto);

    ResponseEntity<?> removeProductToCart(RemoveProductToCartDto removeProductToCartDto);
}
