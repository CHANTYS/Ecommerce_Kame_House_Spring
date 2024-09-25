package com.KameHouse.ecom.controller.customer;

import com.KameHouse.ecom.dto.OrderedProductsResponseDto;
import com.KameHouse.ecom.dto.ReviewDto;
import com.KameHouse.ecom.service.customer.review.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/ordered-products/{orderId}")
    public ResponseEntity<OrderedProductsResponseDto> getOrderedProductsDetailsByOrderId(@PathVariable Long orderId) {
        OrderedProductsResponseDto orderedProductsResponseDto = reviewService.getOrderedProductsDetailsByOrderId(orderId);
        return ResponseEntity.ok(orderedProductsResponseDto);
    }

    @PostMapping("/review")
    public ResponseEntity<?> giveReview(@ModelAttribute ReviewDto reviewDto) throws IOException {
        ReviewDto reviewedDto = reviewService.giveReview(reviewDto);
        if (reviewedDto == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Something went wrong");
        return ResponseEntity.status(HttpStatus.CREATED).body(reviewedDto);
    }

}
