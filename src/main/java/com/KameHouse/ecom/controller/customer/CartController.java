package com.KameHouse.ecom.controller.customer;

import com.KameHouse.ecom.dto.*;
import com.KameHouse.ecom.service.customer.cart.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/cart")
    public ResponseEntity<?> addProductToCart(@RequestBody AddCartItemDto cartItemsDto) {
        return cartService.addProductToCart(cartItemsDto);
    }

    @GetMapping("/cart/{userId}")
    public ResponseEntity<?> getCartByUserId(@PathVariable Long userId) {
        return cartService.getCartByUserId(userId);
    }

    @PostMapping("/deduction")
    public ResponseEntity<?> addMinusOnProduct(@RequestBody QuantityChangeProductDto quantityChangeProductDto) {
        return cartService.decreaseProductQuantity(quantityChangeProductDto);
    }

    @PostMapping("/addition")
    public ResponseEntity<?> increaseProductQuantity(@RequestBody QuantityChangeProductDto quantityChangeProductDto) {
        return cartService.increaseProductQuantity(quantityChangeProductDto);
    }
}
