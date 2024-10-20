package com.KameHouse.ecom.service.customer.customerorder;

import com.KameHouse.ecom.dto.OrderDto;
import com.KameHouse.ecom.dto.PlaceOrderDto;
import com.KameHouse.ecom.entity.Order;
import com.KameHouse.ecom.entity.Product;
import com.KameHouse.ecom.entity.User;
import com.KameHouse.ecom.enums.OrderStatus;
import com.KameHouse.ecom.repo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerOrderServiceImpl implements CustomerOrderService {
    private final OrderRepository orderRepository;

    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final CartItemsProductsRepository cartItemsProductsRepository;
    private final ProductRepository productRepository;
    private final CouponRepository couponRepository;
    @Override
    public ResponseEntity<?> PlaceOrder(PlaceOrderDto placeOrderDto) {
        var user = userRepository.findById(placeOrderDto.getUserId());

        if (!user.isPresent())
            return ResponseEntity.notFound().build();

        var existsCartItem = cartRepository.existsByUserId(user.get().getId());

        if (!existsCartItem)
            return ResponseEntity.notFound().build();

        var cartItem = cartRepository.findByUserId(user.get().getId());

        Order order = new Order();
        order.setOrderDescription(placeOrderDto.getOrderDescription());
        order.setDate(new Date());
        order.setStatus(OrderStatus.Placed);
        order.setTrackingId(UUID.randomUUID());
        order.setAddress(placeOrderDto.getAddress());

        Set<Product> productSet = new HashSet<>();
        AtomicReference<Double> totalAmount = new AtomicReference<>(0D);
        AtomicReference<Long> totalQuantity = new AtomicReference<>(0L);

        cartItem.getCartItemsProducts().forEach(x -> {
            productSet.add(x.getProduct());
            totalAmount.updateAndGet(v -> v + x.getProduct().getPrice() * x.getQuantity());
            totalQuantity.updateAndGet(v -> v + x.getQuantity());
        });

        order.setTotalAmount(totalAmount.get());
        order.setPayment(totalAmount.get().toString());
        order.setQuantity(totalQuantity.get());
        order.setUser(user.get());
        order.setProducts(productSet.stream().toList());
        order.setCoupon(cartItem.getCoupon());

        Order newOrder = orderRepository.save(order);

        List<Product> productsWithNewOrder = productSet.stream().map(x -> {
            x.getOrders().add(newOrder);
            return x;
        }).toList();

        productRepository.saveAll(productsWithNewOrder);

        cartItem.setCoupon(null);
        cartItemsProductsRepository.deleteAll(cartItem.getCartItemsProducts());
        cartRepository.delete(cartItem);

        return ResponseEntity.ok(newOrder.getOrderDto());
//        Order order = orderRepository.findByUserIdAndStatus(placeOrderDto.getUserId(), OrderStatus.Pending);
//
//        Optional<User> optionalUser = userRepository.findById(placeOrderDto.getUserId());
//        if (optionalUser.isPresent()) {
//            order.setOrderDescription(placeOrderDto.getOrderDescription());
//            order.setStatus(OrderStatus.Placed);
//            order.setDate(new Date());
//            order.setTrackingId(UUID.randomUUID());
//            order.setAddress(placeOrderDto.getAddress());
////            order.setAmount(order.getAmount());
//            orderRepository.save(order);
//            User user = optionalUser.get();
//            Order newOrder = new Order();
//            newOrder.setAmount(0L);
//            newOrder.setTotalAmount(0L);
//            newOrder.setDiscount(0L);
//            newOrder.setUser(user);
//            newOrder.setStatus(OrderStatus.Pending);
//            orderRepository.save(newOrder);
//            return order.getOrderDto();
//        }
//        return null;
    }

    @Override
    public List<OrderDto> getMyPlacedOrders(Long userId) {
        return orderRepository.findAllByUserIdAndStatusIn(userId, List.of(OrderStatus.Placed, OrderStatus.Shipped, OrderStatus.Delivered)).stream().map(Order::getOrderDto).collect(Collectors.toList());
    }

    @Override
    public OrderDto searchOrderByTrackingId(UUID trackingId) {
        Optional<Order> optionalOrder = orderRepository.findByTrackingId(trackingId);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            return order.getOrderDto();
        }
        return null;
    }

}

