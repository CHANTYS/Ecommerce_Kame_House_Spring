package com.KameHouse.ecom.service.admin.adminproduct;


import com.KameHouse.ecom.dto.ProductDto;
import com.KameHouse.ecom.dto.SecondProductDto;
import com.KameHouse.ecom.entity.Product;

import java.io.IOException;
import java.util.List;

public interface AdminProductService {

    Product addProduct(SecondProductDto secondProductDto) throws IOException;

    List<ProductDto> getAllProducts();

    ProductDto getProductById(Long productId);

    ProductDto updateProduct(Long productId, ProductDto productDto) throws IOException;

    boolean deleteProduct(Long productId);

    List<ProductDto> searchProductByTitle(String title);

}