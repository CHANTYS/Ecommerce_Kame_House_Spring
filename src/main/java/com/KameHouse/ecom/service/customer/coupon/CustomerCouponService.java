package com.KameHouse.ecom.service.customer.coupon;

import com.KameHouse.ecom.dto.OrderDto;

public interface CustomerCouponService {

    OrderDto applyCoupon(Long userId, String code);

}
