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


            CartItemsProducts cartItemsProducts = new CartItemsProducts();
            cartItemsProducts.setCartItems(cartItems);
            cartItemsProducts.setProduct(product);
            cartItemsProducts.setQuantity(1L);

            var savedCart = cartItemsProductsRepository.save(cartItemsProducts);

            cartItems.getCartItemsProducts().add(savedCart);

            AtomicReference<Long> totalQuantity = new AtomicReference<>(0L);

            cartItems.getCartItemsProducts().forEach(x -> {
                totalQuantity.updateAndGet(y -> y + x.getQuantity());
            });

            cartItems.setQuantity(totalQuantity.get());

            AtomicReference<Double> totalAmount = new AtomicReference<>(0D);

            cartItems.getCartItemsProducts().forEach(x -> {
                totalAmount.updateAndGet(y -> y + x.getProduct().getPrice() * x.getQuantity());
            });

            cartItems.setPrice(totalAmount.get());

            cartRepository.save(cartItems);

            return ResponseEntity.status(HttpStatus.CREATED).body(savedCart.GetCartItemDto());
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
            cartItemsProducts.setQuantity(1L);

            CartItemsProducts saved = cartItemsProductsRepository.save(cartItemsProducts);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved.GetCartItemDto());
        }
    }

    @Override
    public ResponseEntity<?> getCartByUserId(Long userId) {
        boolean existsCartItem = cartRepository.existsByUserId(userId);
        if (!existsCartItem)
            return ResponseEntity.badRequest().body("Error when get cart, cart not exists");

        CartItems cartItems = cartRepository.findByUserId(userId);

        return ResponseEntity.ok(cartItems.GetCartItemDto());
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
    }

    @Override
    public ResponseEntity<?> decreaseProductQuantity(QuantityChangeProductDto quantityChangeProductDto) {
        boolean existsCartItem = cartRepository.existsByUserId(quantityChangeProductDto.getUserId());
        if (!existsCartItem)
            return ResponseEntity.badRequest().body("Error when increase product, product not exists");

        CartItems cartItems = cartRepository.findByUserId(quantityChangeProductDto.getUserId());
        boolean existsProduct = cartItems.getCartItemsProducts()
                .stream()
                .anyMatch(x -> x.getProduct().getId().equals(quantityChangeProductDto.getProductId()));
        if (!existsProduct)
            return ResponseEntity.badRequest().body("Error when increase product, product not exists");

        CartItemsProducts cartItemsProducts = cartItems.getCartItemsProducts()
                .stream()
                .filter(x -> x.getProduct().getId().equals(quantityChangeProductDto.getProductId()))
                .findFirst()
                .get();

        if (cartItemsProducts.getQuantity() == 1)
            return ResponseEntity.noContent().build();

        cartItemsProducts.setQuantity(cartItemsProducts.getQuantity() - 1L);
        cartItems.setQuantity(cartItems.getQuantity() - 1L);
        AtomicReference<Double> totalAmount = new AtomicReference<>(0D);

        cartItems.getCartItemsProducts().forEach(x -> {
            totalAmount.updateAndGet(y -> y + x.getProduct().getPrice() * x.getQuantity());
        });

        cartItems.setPrice(totalAmount.get());

        cartItemsProducts.setCartItems(cartItems);

        cartItemsProductsRepository.save(cartItemsProducts);

        return ResponseEntity.noContent().build();
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
    }

    @Override
    public ResponseEntity<?> increaseProductQuantity(QuantityChangeProductDto quantityChangeProductDto) {
        boolean existsCartItem = cartRepository.existsByUserId(quantityChangeProductDto.getUserId());
        if (!existsCartItem)
            return ResponseEntity.badRequest().body("Error when increase product, product not exists");

        CartItems cartItems = cartRepository.findByUserId(quantityChangeProductDto.getUserId());
        boolean existsProduct = cartItems.getCartItemsProducts()
                                        .stream()
                                        .anyMatch(x -> x.getProduct().getId().equals(quantityChangeProductDto.getProductId()));
        if (!existsProduct)
            return ResponseEntity.badRequest().body("Error when increase product, product not exists");

        CartItemsProducts cartItemsProducts = cartItems.getCartItemsProducts()
                                                        .stream()
                                                        .filter(x -> x.getProduct().getId().equals(quantityChangeProductDto.getProductId()))
                                                        .findFirst()
                                                        .get();

        cartItemsProducts.setQuantity(cartItemsProducts.getQuantity() + 1L);
        cartItems.setQuantity(cartItems.getQuantity() + 1L);
        AtomicReference<Double> totalAmount = new AtomicReference<>(0D);

        cartItems.getCartItemsProducts().forEach(x -> {
            totalAmount.updateAndGet(y -> y + x.getProduct().getPrice() * x.getQuantity());
        });

        cartItems.setPrice(totalAmount.get());

        cartItemsProducts.setCartItems(cartItems);

        cartItemsProductsRepository.save(cartItemsProducts);

        return ResponseEntity.noContent().build();
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
    }

    @Override
    public ResponseEntity<?> removeProductToCart(RemoveProductToCartDto removeProductToCartDto) {
        boolean existsCartItem = cartRepository.existsByUserId(removeProductToCartDto.getUserId());
        if (!existsCartItem)
            return ResponseEntity.badRequest().body("Error when increase product, product not exists");

        CartItems cartItems = cartRepository.findByUserId(removeProductToCartDto.getUserId());
        boolean existsProduct = cartItems.getCartItemsProducts()
                .stream()
                .anyMatch(x -> x.getProduct().getId().equals(removeProductToCartDto.getProductId()));
        if (!existsProduct)
            return ResponseEntity.badRequest().body("Error when increase product, product not exists");


        CartItemsProducts cartItemsProducts = cartItems.getCartItemsProducts()
                                                        .stream()
                                                        .filter(x -> x.getProduct().getId().equals(removeProductToCartDto.getProductId()))
                                                        .findFirst().get();

        cartItems.setQuantity(cartItems.getQuantity() - cartItemsProducts.getQuantity());
        cartItems.setPrice(cartItems.getPrice() - (cartItemsProducts.getQuantity() * cartItemsProducts.getProduct().getPrice()));

        cartItems.getCartItemsProducts().remove(cartItemsProducts);

        cartItemsProductsRepository.delete(cartItemsProducts);
        cartRepository.delete(cartItems);

        return ResponseEntity.noContent().build();
    }
}