package com.KameHouse.ecom.service.customer.review;


import com.KameHouse.ecom.dto.OrderedProductsResponseDto;
import com.KameHouse.ecom.dto.ReviewDto;

import java.io.IOException;

public interface ReviewService {


    OrderedProductsResponseDto getOrderedProductsDetailsByOrderId(Long orderId);

    ReviewDto giveReview(ReviewDto reviewDto) throws IOException;
}
