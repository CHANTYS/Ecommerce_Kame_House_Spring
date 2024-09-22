package com.KameHouse.ecom.dto;

import com.KameHouse.ecom.entity.Order;
import com.KameHouse.ecom.entity.Product;
import com.KameHouse.ecom.entity.User;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static org.hibernate.annotations.OnDeleteAction.CASCADE;

@Data
public class CartItemsDto {
    private Long id;

    private Long price;

    private Long quantity;

    private Long productId;

    private Long orderId;

    private String productName;

    private byte[] returnedImg;

    private Long userId;

}
