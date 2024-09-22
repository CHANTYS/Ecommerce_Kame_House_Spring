package com.KameHouse.ecom.controller.customer;

import com.KameHouse.ecom.dto.AddProductInCartDto;
import com.KameHouse.ecom.dto.OrderDto;
import com.KameHouse.ecom.services.coustomer.cart.CartService;
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
    public ResponseEntity<?> addProductToCart(@RequestBody AddProductInCartDto addProductInCartDto) {
        return cartService.addProductInCart(addProductInCartDto);
    }

    @GetMapping("/cart/{userId}")
    public ResponseEntity<OrderDto> getCardByUserId(@PathVariable Long userId) {
        var orderDto = cartService.getCartByUserId(userId);

        return ResponseEntity.status(HttpStatus.OK).body(orderDto);
    }
}
