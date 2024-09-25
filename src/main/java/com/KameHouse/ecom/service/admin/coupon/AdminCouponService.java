package com.KameHouse.ecom.service.admin.coupon;


import com.KameHouse.ecom.entity.Coupon;

import java.util.List;

public interface AdminCouponService {

    Coupon createCoupon(Coupon coupon);

    List<Coupon> getAllCoupons();
}
