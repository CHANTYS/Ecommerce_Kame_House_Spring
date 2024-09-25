package com.KameHouse.ecom.service.customer.customerorder;

import com.KameHouse.ecom.dto.OrderDto;
import com.KameHouse.ecom.dto.PlaceOrderDto;

import java.util.List;
import java.util.UUID;

public interface CustomerOrderService {

    OrderDto PlaceOrder(PlaceOrderDto placeOrderDto);

    List<OrderDto> getMyPlacedOrders(Long userId);

    OrderDto searchOrderByTrackingId(UUID trackingId);

}
