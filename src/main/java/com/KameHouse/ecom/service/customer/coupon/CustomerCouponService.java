package com.KameHouse.ecom.service.customer.coupon;

import org.springframework.http.ResponseEntity;

public interface CustomerCouponService {

    ResponseEntity<?> applyCoupon(Long userId, String code);

}
