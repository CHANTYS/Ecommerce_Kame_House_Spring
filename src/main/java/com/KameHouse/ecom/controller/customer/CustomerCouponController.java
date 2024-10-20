package com.KameHouse.ecom.controller.customer;

import com.KameHouse.ecom.dto.OrderDto;
import com.KameHouse.ecom.exceptions.ValidationException;
import com.KameHouse.ecom.service.customer.coupon.CustomerCouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
public class CustomerCouponController {
    private final CustomerCouponService customerCouponService;

    @GetMapping("/coupon/{userId}/{code}")
    public ResponseEntity<?> applyCoupon(@PathVariable Long userId, @PathVariable String code) {
        return customerCouponService.applyCoupon(userId,code);
    }
}
