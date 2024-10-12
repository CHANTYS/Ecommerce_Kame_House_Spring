package com.KameHouse.ecom.service.customer.cart;

import com.KameHouse.ecom.dto.*;
import com.KameHouse.ecom.entity.*;
import com.KameHouse.ecom.enums.OrderStatus;
import com.KameHouse.ecom.repo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {


    private final OrderRepository orderRepository;

    private final UserRepository userRepository;

    private final ProductRepository productRepository;

    private final CartRepository cartRepository;

    private final CartItemsProductsRepository cartItemsProductsRepository;

    private final CategoryRepository categoryRepository;

    @Override
    public ResponseEntity<?> addProductToCart(AddCartItemDto cartItemsDto) {
        boolean existsUser = userRepository.existsById(cartItemsDto.getUserId());
        if (!existsUser)
            return ResponseEntity.badRequest().body("Error when update cart, user not exists");

        boolean exists = productRepository.existsById(cartItemsDto.getProductId());
        if (!exists)
            return ResponseEntity.badRequest().body("Error when update cart, products not exists");

        CartItems cartItems;
        boolean existsCart = cartRepository.existsByUserId(cartItemsDto.getUserId());
        if (existsCart) {
            cartItems = cartRepository.findByUserId(cartItemsDto.getUserId());

            boolean existsProdOnCart = cartItems.getCartItemsProducts()
                                            .stream()
                                            .anyMatch(product -> product.getCartItemsProductsKey().getProductId().equals(cartItemsDto.getProductId()));

            if (existsProdOnCart)
                return ResponseEntity.badRequest().body("Error when update cart, product exists");

            Product product = productRepository.findById(cartItemsDto.getProductId()).get();

            Set<Product> products = cartItems.getCartItemsProducts().stream().map(CartItemsProducts::getProduct).collect(Collectors.toSet());
            products.add(product);

            cartItems.setQuantity((long)products.size());

            AtomicReference<Double> totalPrice = new AtomicReference<>(0D);
            products.forEach(x -> {
                totalPrice.updateAndGet(v -> v + x.getPrice());
            });

            cartItems.setPrice(totalPrice.get());

            CartItemsProducts cartItemsProducts = new CartItemsProducts();
            cartItemsProducts.setProduct(product);
            cartItemsProducts.setCartItems(cartItems);

            var savedCart = cartItemsProductsRepository.save(cartItemsProducts);

            return ResponseEntity.status(HttpStatus.CREATED).body(savedCart.getCartItems());
        }
        else {
            cartItems = new CartItems();

            User user1 = new User();
            user1.setId(cartItemsDto.getUserId());

            cartItems.setUser(user1);

            Product product = productRepository.findById(cartItemsDto.getProductId()).get();

            cartItems.setPrice(product.getPrice());
            cartItems.setQuantity(1L);

            CartItems savedCart = cartRepository.save(cartItems);

            CartItemsProducts cartItemsProducts = new CartItemsProducts();
            cartItemsProducts.setCartItems(savedCart);
            cartItemsProducts.setProduct(product);

            CartItemsProducts saved = cartItemsProductsRepository.save(cartItemsProducts);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        }
    }

    @Override
    public ResponseEntity<?> getCartByUserId(Long userId) {
        boolean existsCartItem = cartRepository.existsByUserId(userId);
        if (!existsCartItem)
            return ResponseEntity.badRequest().body("Error when get cart, cart not exists");

        CartItems cartItems = cartRepository.findByUserId(userId);

        GetCartItemDto response = new GetCartItemDto();
        response.setId(cartItems.getId());
        response.setQuantity(cartItems.getQuantity());
        response.setPrice(cartItems.getPrice());

        cartItems.getCartItemsProducts().forEach(item -> {
            GetProductDto productDto = new GetProductDto();
            productDto.setProductName(item.getProduct().getName());
            productDto.setReturnedImg(item.getProduct().getImg());
            productDto.setQuantity(item.getQuantity());
            productDto.setId(item.getProduct().getId());
            productDto.setPrice(item.getProduct().getPrice());
            response.getProductDtos().add(productDto);
        });
        //        Order order = orderRepository.findByUserIdAndStatus(userId, OrderStatus.Pending);
      /*  List<CartItemsDto> cartItemsDtos = order.getCartItems().stream().map(CartItems::getCartDto).collect(Collectors.toList());
        OrderDto orderDto = new OrderDto();
        orderDto.setCartItems(cartItemsDtos);
        orderDto.setAmount(order.getAmount());
        orderDto.setId(order.getId());
        orderDto.setStatus(order.getStatus());
        orderDto.setDiscount(order.getDiscount());
        if(order.getCoupon() != null){
            orderDto.setCouponName(order.getCoupon().getName());
        }

        orderDto.setTotalAmount(order.getTotalAmount());     */
        return ResponseEntity.ok(response);
    }

    @Override
    public OrderDto decreaseProductQuantity(QuantityChangeProductDto quantityChangeProductDto) {
//        Order order = orderRepository.findByUserIdAndStatus(quantityChangeProductDto.getUserId(), OrderStatus.Pending);
//        Optional<Product> optionalProduct = productRepository.findById(quantityChangeProductDto.getProductId());
//        Optional<CartItems> optionalCartItem = cartRepository.findByProductIdAndUserId(quantityChangeProductDto.getProductId(), quantityChangeProductDto.getUserId());
//        CartItems cartItem = optionalCartItem.get();
//        order.setAmount(order.getAmount() - optionalProduct.get().getPrice());
//        order.setTotalAmount(order.getTotalAmount() - optionalProduct.get().getPrice());
//        cartItem.setQuantity(optionalCartItem.get().getQuantity() - 1);
//
//        if(order.getCoupon() != null){
//            double discountAmount = ((order.getCoupon().getDiscount() / 100.0) * order.getTotalAmount());
//            double netAmount = order.getTotalAmount() - discountAmount;
//
//            long discountAmountLong = (long) discountAmount;
//            long netAmountLong = (long) netAmount;
//
//            order.setAmount(netAmountLong);
//            order.setDiscount(discountAmountLong);
//        }
//
//        cartRepository.save(cartItem);
//        orderRepository.save(order);
//        return order.getOrderDto();

        return new OrderDto();
    }

    @Override
    public OrderDto increaseProductQuantity(QuantityChangeProductDto quantityChangeProductDto) {
//        Order order = orderRepository.findByUserIdAndStatus(quantityChangeProductDto.getUserId(), OrderStatus.Pending);
//        Optional<Product> optionalProduct = productRepository.findById(quantityChangeProductDto.getProductId());
//        Optional<CartItems> optionalCartItem = cartRepository.findByProductIdAndUserId(quantityChangeProductDto.getProductId(), quantityChangeProductDto.getUserId());
//        CartItems cartItem = optionalCartItem.get();
//        Product product = optionalProduct.get();
//        order.setAmount(order.getAmount() + optionalProduct.get().getPrice());
//        order.setTotalAmount(order.getTotalAmount() + optionalProduct.get().getPrice());
//        cartItem.setQuantity(optionalCartItem.get().getQuantity() + 1);
//
//        if(order.getCoupon() != null){
//            double discountAmount = ((order.getCoupon().getDiscount() / 100.0) * order.getTotalAmount());
//            double netAmount = order.getTotalAmount() - discountAmount;
//
//            long discountAmountLong = (long) discountAmount;
//            long netAmountLong = (long) netAmount;
//
//            order.setAmount(netAmountLong);
//            order.setDiscount(discountAmountLong);
//        }
//
//        cartRepository.save(cartItem);
//        orderRepository.save(order);
//        return order.getOrderDto();
        return new OrderDto();
    }
}