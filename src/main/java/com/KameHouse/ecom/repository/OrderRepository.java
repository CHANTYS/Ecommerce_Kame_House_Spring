package com.KameHouse.ecom.repository;

import com.KameHouse.ecom.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Order findByUserIdAndOrderStatus(Long id, com.KameHouse.ecom.enums.OrderStatus orderStatus);
}
