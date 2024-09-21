package com.KameHouse.ecom.repository;

import com.KameHouse.ecom.entity.CartItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Repository
public interface CartItemsRepository extends JpaRepository<CartItems, Long> {
    Optional<CartItems> findByProductIdAndOrderIdAndUserId(Long productId, Long orderId, Long userId);
}
