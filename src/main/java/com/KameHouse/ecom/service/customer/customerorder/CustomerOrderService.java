package com.KameHouse.ecom.service.customer.customerorder;

import com.KameHouse.ecom.dto.OrderDto;
import com.KameHouse.ecom.dto.PlaceOrderDto;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface CustomerOrderService {

    ResponseEntity<?> PlaceOrder(PlaceOrderDto placeOrderDto);

    List<OrderDto> getMyPlacedOrders(Long userId);

    OrderDto searchOrderByTrackingId(UUID trackingId);

}
