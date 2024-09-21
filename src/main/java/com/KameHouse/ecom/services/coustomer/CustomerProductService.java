package com.KameHouse.ecom.services.coustomer;

import com.KameHouse.ecom.dto.ProductDto;

import java.util.List;

public interface CustomerProductService {
    List<ProductDto> getAllProduct();
    List<ProductDto> searchProductByName(String name);
}
