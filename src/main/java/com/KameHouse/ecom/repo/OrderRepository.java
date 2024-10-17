package com.KameHouse.ecom.repo;

import com.KameHouse.ecom.entity.Order;
import com.KameHouse.ecom.entity.User;
import com.KameHouse.ecom.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {

     Order findByUserAndStatus(User user, OrderStatus status);

     Order findByUserIdAndStatus(Long userId, OrderStatus status);

     List<Order> findAllByUserIdAndStatusIn(Long userId, List<OrderStatus> orderStatusList);

     List<Order> findAllByStatusIn(List<OrderStatus> orderStatusList);

    Optional<Order> findByTrackingId(UUID trackingId);

    List<Order> findByDateBetweenAndStatus(Date startOfMonth, Date endOfMonth, OrderStatus status);

    Long countByStatus(OrderStatus status);

}
