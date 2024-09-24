package com.KameHouse.ecom.services.admin.adminproduct;

import com.KameHouse.ecom.dto.ProductDto;
import com.KameHouse.ecom.dto.ProductResponseDto;

import java.io.IOException;
import java.util.List;

public interface AdminProductService {

    ProductDto addProduct(ProductResponseDto productResponseDto) throws IOException;

    List<ProductDto> getAllProduct();
    List<ProductDto> getAllProductByName(String name);

    boolean deleteProduct(Long id);
}
