package com.KameHouse.ecom.service.customer.coupon;


import com.KameHouse.ecom.dto.OrderDto;
import com.KameHouse.ecom.entity.Coupon;
import com.KameHouse.ecom.entity.Order;
import com.KameHouse.ecom.enums.OrderStatus;
import com.KameHouse.ecom.exceptions.ValidationException;
import com.KameHouse.ecom.repo.CartRepository;
import com.KameHouse.ecom.repo.CouponRepository;
import com.KameHouse.ecom.repo.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class CustomerCouponServiceImpl implements CustomerCouponService {

    private final CouponRepository couponRepository;

    private final CartRepository cartRepository;


    public ResponseEntity<?> applyCoupon(Long userId, String code) {
        Coupon coupon = couponRepository.findByCode(code).orElseThrow(() -> new ValidationException("Coupon not found."));

        if (couponIsExpired(coupon))
            return ResponseEntity.notFound().build();

        var cartItems = cartRepository.findByUserId(userId);

        double discountAmount = ((coupon.getDiscount() / 100.0) * cartItems.getTotalAmount());
        double netAmount = cartItems.getTotalAmount() - discountAmount;

        cartItems.setDiscount(discountAmount);
        cartItems.setTotalAmount(netAmount);
        cartItems.setCoupon(coupon);

        cartRepository.save(cartItems);
        return ResponseEntity.ok().body(cartItems.GetCartItemDto());
    }

    private boolean couponIsExpired(Coupon coupon) {
        Date currentDate = new Date();
        Date expirationDate = coupon.getExpirationDate();
        return expirationDate != null && currentDate.after(expirationDate);
    }
}
