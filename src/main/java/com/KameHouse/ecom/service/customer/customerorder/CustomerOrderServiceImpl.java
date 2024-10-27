package com.KameHouse.ecom.service.customer.customerorder;

import com.KameHouse.ecom.dto.OrderDto;
import com.KameHouse.ecom.dto.PlaceOrderDto;
import com.KameHouse.ecom.entity.Order;
import com.KameHouse.ecom.entity.Product;
import com.KameHouse.ecom.enums.OrderStatus;
import com.KameHouse.ecom.repo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.preference.PreferenceBackUrlsRequest;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.resources.preference.Preference;

@Service
@RequiredArgsConstructor
public class CustomerOrderServiceImpl implements CustomerOrderService {
    private final OrderRepository orderRepository;

    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final CartItemsProductsRepository cartItemsProductsRepository;
    private final ProductRepository productRepository;

    @Override
    public ResponseEntity<?> CreatePreferenceMP(Long userId) {
        var user = userRepository.findById(userId);

        if (!user.isPresent())
            return ResponseEntity.notFound().build();

        var existsCartItem = cartRepository.existsByUserId(user.get().getId());

        if (!existsCartItem)
            return ResponseEntity.notFound().build();

        var cartItem = cartRepository.findByUserId(user.get().getId());
        
        MercadoPagoConfig.setAccessToken("APP_USR-3766041123444579-102700-35dd8c7776eb5359f625f78f8e3fe4f6-2058551287");
        
        
        List<PreferenceItemRequest> items = new ArrayList<>();

        cartItem.getCartItemsProducts().forEach(x -> {
            Product product = x.getProduct();
            PreferenceItemRequest itemRequest = PreferenceItemRequest.builder()
                                                                     .title(product.getName())
                                                                     .description(product.getDescription())
                                                                     .categoryId(product.getCategory().getName())
                                                                     .quantity(Integer.parseInt(x.getQuantity().toString()))
                                                                     .currencyId("ARS")
                                                                     .unitPrice(new BigDecimal(product.getPrice()))
                                                                     .build();

            items.add(itemRequest);
        });

        PreferenceBackUrlsRequest backUrls = PreferenceBackUrlsRequest.builder()
                                                                      .success("http://localhost:4200/customer/purchase-mp")
                                                                      .pending("http://localhost:4200/customer/purchase-mp")
                                                                      .failure("http://localhost:4200/customer/purchase-mp")
                                                                      .build();

        PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                                                                .items(items)
                                                                .backUrls(backUrls)
                                                                .build();
        PreferenceClient client = new PreferenceClient();

        try {
            Preference preference = client.create(preferenceRequest);
            return ResponseEntity.ok(preference);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("ocurrio un error en el pago");
        }
    }


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
        AtomicReference<Long> totalQuantity = new AtomicReference<>(0L);

        cartItem.getCartItemsProducts().forEach(x -> {
            productSet.add(x.getProduct());
            totalQuantity.updateAndGet(v -> v + x.getQuantity());
        });

        order.setTotalAmount(cartItem.getTotalAmount());
        order.setPayment(cartItem.getTotalAmount().toString());
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
        return orderRepository.findAllByUserIdAndStatusIn(userId, List.of(OrderStatus.Placed, OrderStatus.Shipped, OrderStatus.Delivered))
                            .stream()
                            .map(Order::getOrderDto)
                            .sorted(Comparator.nullsLast(
                                        (o1, o2) -> o2.getDate().compareTo(o1.getDate())))
                            .collect(Collectors.toList());
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

