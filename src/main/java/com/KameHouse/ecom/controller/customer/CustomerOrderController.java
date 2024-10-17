package com.KameHouse.ecom.controller.customer;

import com.KameHouse.ecom.dto.OrderDto;
import com.KameHouse.ecom.dto.PlaceOrderDto;
import com.KameHouse.ecom.service.customer.customerorder.CustomerOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
public class CustomerOrderController {

    private final CustomerOrderService customerOrderService;

    @PostMapping("/placeOrder")
    public ResponseEntity<?> placeOrder(@RequestBody PlaceOrderDto placeOrderDto) {
        return customerOrderService.PlaceOrder(placeOrderDto);
    }

    @GetMapping("/myOrders/{userId}")
    public ResponseEntity<List<OrderDto>> getMyPlacedOrders(@PathVariable Long userId) {
        List<OrderDto> orderDtos = customerOrderService.getMyPlacedOrders(userId);
        return ResponseEntity.ok(orderDtos);
    }

}
