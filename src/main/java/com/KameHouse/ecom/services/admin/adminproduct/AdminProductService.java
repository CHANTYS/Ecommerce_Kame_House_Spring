package com.KameHouse.ecom.services.admin.adminproduct;

import com.KameHouse.ecom.dto.ProductDto;

import java.io.IOException;
import java.util.List;

public interface AdminProductService {

    ProductDto addProduct(ProductDto productDto) throws IOException;

    List<ProductDto> getAllProduct();
}
