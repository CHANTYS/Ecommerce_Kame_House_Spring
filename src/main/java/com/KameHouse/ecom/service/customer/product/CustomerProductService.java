package com.KameHouse.ecom.service.customer.product;

import com.KameHouse.ecom.dto.CategoryDto;
import com.KameHouse.ecom.dto.CompleteProductDetailDto;
import com.KameHouse.ecom.dto.ProductDto;

import java.util.List;

public interface CustomerProductService {

    List<CategoryDto> getAllCategories();

    List<ProductDto> searchProductByTitle(String title);

    List<ProductDto> getAllProducts();

    List<ProductDto> getProductsByCategory(Long categoryId);

    CompleteProductDetailDto getCompleteProductDetailById(Long productId);
}
