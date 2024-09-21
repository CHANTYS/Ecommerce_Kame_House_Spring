package com.KameHouse.ecom.services.coustomer.cart;

import com.KameHouse.ecom.dto.AddProductInCartDto;
import com.KameHouse.ecom.repository.CartItemsRepository;
import com.KameHouse.ecom.repository.OrderRepository;
import com.KameHouse.ecom.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

public interface CartService {
    ResponseEntity<?> addProductInCart(AddProductInCartDto addProductInCartDto);
}
