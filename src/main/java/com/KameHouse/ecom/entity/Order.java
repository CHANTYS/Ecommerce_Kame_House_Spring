package com.KameHouse.ecom.entity;

import com.KameHouse.ecom.dto.OrderDto;
import com.KameHouse.ecom.enums.OrderStatus;
import lombok.Data;
import jakarta.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String orderDescription;

    private Date date;

    private Long quantity;

    private String address;

    private String payment;

    private OrderStatus status;

    private Double totalAmount;

    private Double discount;

    private UUID trackingId;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "coupon_id", referencedColumnName = "id")
    private Coupon coupon;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToMany
    private List<Product> products;

    public OrderDto getOrderDto() {
        OrderDto orderDto = new OrderDto();
        orderDto.setId(id);
        orderDto.setOrderDescription(orderDescription);
        orderDto.setAddress(address);
        orderDto.setTrackingId(trackingId);
        orderDto.setQuantity(quantity);
        orderDto.setDate(date);
        orderDto.setPayment(payment);
        orderDto.setStatus(status);
        orderDto.setUserName(user.getName());
        orderDto.setTotalAmount(totalAmount);
        if(coupon != null) {
            orderDto.setCouponName(coupon.getName());
        }
        return orderDto;
    }

}
