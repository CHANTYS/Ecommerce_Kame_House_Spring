package com.KameHouse.ecom.repo;

import com.KameHouse.ecom.entity.CartItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<CartItems,Long> {

//    Optional<CartItems> findByProductIdAndUserId(Long productId, Long userId);

//    List<CartItems> findByOrderId(Long orderId);
    CartItems findByUserId(Long userId);
    boolean existsByUserId(Long userId);
}
