package com.KameHouse.ecom.service.customer.cart;

import com.KameHouse.ecom.dto.CartItemsDto;
import com.KameHouse.ecom.dto.OrderDto;
import com.KameHouse.ecom.dto.PlaceOrderDto;
import com.KameHouse.ecom.dto.QuantityChangeProductDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CartService {


    ResponseEntity<?> addProductToCart(CartItemsDto cartItemsDto);

    OrderDto getCartByUserId(Long userId);

    OrderDto decreaseProductQuantity(QuantityChangeProductDto quantityChangeProductDto);

    OrderDto increaseProductQuantity(QuantityChangeProductDto quantityChangeProductDto);


}
