package com.KameHouse.ecom.service.admin.adminproduct;

import com.KameHouse.ecom.dto.ProductDto;
import com.KameHouse.ecom.dto.AddProductDto;
import com.KameHouse.ecom.entity.Category;
import com.KameHouse.ecom.entity.Product;
import com.KameHouse.ecom.repo.CategoryRepository;
import com.KameHouse.ecom.repo.ProductRepository;
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

    @Override
    public Product addProduct(AddProductDto addProductDto) throws IOException {
        Product product = new Product();
        product.setName(addProductDto.getName());
        product.setPrice(addProductDto.getPrice());
        product.setDescription(addProductDto.getDescription());
        product.setImg(addProductDto.getImg().getBytes());
        Category category = categoryRepository.findById(Long.parseLong(addProductDto.getCategoryId())).orElseThrow(() -> new RuntimeException("Category not found"));
        product.setCategory(category);
        return productRepository.save(product);
    }

    @Override
    public List<ProductDto> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(product -> {
            ProductDto productDto = new ProductDto();
            productDto.setId(product.getId());
            productDto.setName(product.getName());
            productDto.setDescription(product.getDescription());
            productDto.setPrice(product.getPrice());
            productDto.setCategoryId(product.getCategory().getId());
            productDto.setCategoryName(product.getCategory().getName());
            productDto.setReturnedImg(product.getImg());
            return productDto;
        }).collect(Collectors.toList());
    }

    @Override
    public ProductDto getProductById(Long productId) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isPresent()) {
            return optionalProduct.get().getProductDto();
        } else {
            return null;
        }
    }

    @Override
    public ProductDto updateProduct(Long productId, ProductDto productDto) throws IOException {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        Optional<Category> category = categoryRepository.findById(productDto.getCategoryId());
        if (optionalProduct.isPresent() && category.isPresent()) {
            Product product = optionalProduct.get();
            product.setName(productDto.getName());
            product.setPrice(productDto.getPrice());
            product.setDescription(productDto.getDescription());
            if (productDto.getImg() != null) {
                product.setImg(productDto.getImg().getBytes());
            }
            product.setCategory(category.get());
            return productRepository.save(product).getProductDto();
        } else {
            return null;
        }
    }

//    @Override
//    public void deleteProduct(Long productId) {
//        Optional<Product> optionalProduct = productRepository.findById(productId);
//        if (optionalProduct.isEmpty()) {
//            throw new IllegalArgumentException("Product with id " + productId + " not found");
//        }
//        productRepository.deleteById(productId);
//    }

    public boolean deleteProduct(Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            productRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<ProductDto> searchProductByTitle(String title) {
        List<Product> products = productRepository.findAllByNameContaining(title);
        return products.stream().map(product -> {
            ProductDto productDto = new ProductDto();
            productDto.setId(product.getId());
            productDto.setName(product.getName());
            productDto.setDescription(product.getDescription());
            productDto.setPrice(product.getPrice());
            productDto.setCategoryId(product.getCategory().getId());
            productDto.setCategoryName(product.getCategory().getName());
            productDto.setReturnedImg(product.getImg());
            return productDto;
        }).collect(Collectors.toList());
    }

}
