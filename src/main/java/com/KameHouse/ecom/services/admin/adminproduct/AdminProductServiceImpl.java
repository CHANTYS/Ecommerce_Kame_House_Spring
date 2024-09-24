package com.KameHouse.ecom.services.admin.adminproduct;


import com.KameHouse.ecom.dto.ProductDto;
import com.KameHouse.ecom.dto.ProductResponseDto;
import com.KameHouse.ecom.entity.Category;
import com.KameHouse.ecom.entity.Product;
import com.KameHouse.ecom.repository.CategoryRepository;
import com.KameHouse.ecom.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminProductServiceImpl implements AdminProductService {


    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;

    public ProductDto addProduct(ProductResponseDto productResponseDto) throws IOException {
        Product product = new Product();
        product.setName(productResponseDto.getName());
        product.setDescription(productResponseDto.getDescription());
        product.setPrice(productResponseDto.getPrice());
        product.setImg(productResponseDto.getImg().getBytes());

        Optional<Category> category = categoryRepository.findById(productResponseDto.getCategoryId());

        category.ifPresent(product::setCategory);

        return productRepository.save(product).getDto();
    }

    public List<ProductDto> getAllProduct(){
        List<Product> products = productRepository.findAll();
        return products.stream().map(Product::getDto).collect(Collectors.toList());
    }

    public List<ProductDto> getAllProductByName(String name) {
        List<Product> products = productRepository.findAllByNameContaining(name);
        return products.stream().map(Product::getDto).collect(Collectors.toList());
    }

    public boolean deleteProduct(Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if(optionalProduct.isPresent()) {
            productRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

}
