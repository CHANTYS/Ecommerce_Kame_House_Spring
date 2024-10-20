package com.KameHouse.ecom.dto;

import com.KameHouse.ecom.enums.OrderStatus;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.UUID;


@Data
public class OrderDto {

    private String orderDescription;

    private List<CartItemsDto> cartItems;

    private Long id;

    private Date date;

    private UUID trackingId;

    private Long quantity;

    private String address;

    private OrderStatus status;

    private String payment;

    private String userName;

    private Double totalAmount;

    private Long discount;
    private String couponName;
}
