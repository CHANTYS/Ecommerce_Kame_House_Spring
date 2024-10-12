package com.KameHouse.ecom.controller.customer;

import com.KameHouse.ecom.dto.AddCartItemDto;
import com.KameHouse.ecom.dto.CartItemsDto;
import com.KameHouse.ecom.dto.OrderDto;
import com.KameHouse.ecom.dto.QuantityChangeProductDto;
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
    public ResponseEntity<OrderDto> getCartByUserId(@PathVariable Long userId) {
        OrderDto orderDto = cartService.getCartByUserId(userId);
        return ResponseEntity.status(HttpStatus.OK).body(orderDto);
    }

    @PostMapping("/deduction")
    public ResponseEntity<OrderDto> addMinusOnProduct(@RequestBody QuantityChangeProductDto quantityChangeProductDto) {
        OrderDto orderDto = cartService.decreaseProductQuantity(quantityChangeProductDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(orderDto);
    }

    @PostMapping("/addition")
    public ResponseEntity<OrderDto> increaseProductQuantity(@RequestBody QuantityChangeProductDto quantityChangeProductDto) {
        OrderDto OrderDto = cartService.increaseProductQuantity(quantityChangeProductDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(OrderDto);
    }

}
