package com.KameHouse.ecom.service.admin.adminorder;


import com.KameHouse.ecom.dto.AnalyticsResponse;
import com.KameHouse.ecom.dto.OrderDto;

import java.util.List;

public interface AdminOrderService {

    List<OrderDto> getAllPlacedOrders();
    OrderDto changeOrderStatus(Long orderId, String status);

    AnalyticsResponse calculateAnalytics();
}
