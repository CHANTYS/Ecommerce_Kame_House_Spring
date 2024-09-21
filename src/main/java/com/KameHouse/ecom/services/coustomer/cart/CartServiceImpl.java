package com.KameHouse.ecom.services.coustomer.cart;

import com.KameHouse.ecom.dto.AddProductInCartDto;
import com.KameHouse.ecom.entity.CartItems;
import com.KameHouse.ecom.enums.OrderStatus;
import com.KameHouse.ecom.repository.CartItemsRepository;
import com.KameHouse.ecom.repository.OrderRepository;
import com.KameHouse.ecom.repository.ProductRepository;
import com.KameHouse.ecom.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final OrderRepository orderRepository;

    private final UserRepository userRepository;

    private final CartItemsRepository cartItemsRepository;

    private final ProductRepository productRepository;

    public ResponseEntity<?> addProductInCart(AddProductInCartDto addProductInCartDto) {
        var activeOrder = orderRepository.findByUserIdAndOrderStatus(addProductInCartDto.getUserId(), OrderStatus.PENDING);
        var optionalCartItems = cartItemsRepository.findByProductIdAndOrderIdAndUserId(addProductInCartDto.getProductId(), activeOrder.getId(), addProductInCartDto.getUserId());

        if (optionalCartItems.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(null);
        }

        var optionalProduct = productRepository.findById(addProductInCartDto.getProductId());
        var optionalUser = userRepository.findById(addProductInCartDto.getUserId());
        if(optionalProduct.isPresent()) {
            var cart = new CartItems();
            cart.setProduct(optionalProduct.get());
            cart.setQuantity(1L);
            cart.setPrice(optionalProduct.get().getPrice());
            cart.setUser(optionalUser.get());
            cart.setOrder(activeOrder);

            CartItems updatedCart = cartItemsRepository.save(cart);

            activeOrder.setTotalAmount(activeOrder.getTotalAmount() + cart.getPrice());
            activeOrder.setAmount(activeOrder.getAmount() + cart.getPrice());
            activeOrder.getCartItems().add(cart);

            orderRepository.save(activeOrder);

            return ResponseEntity.status(HttpStatus.CREATED).body(cart);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User or product not found");
    }

}
