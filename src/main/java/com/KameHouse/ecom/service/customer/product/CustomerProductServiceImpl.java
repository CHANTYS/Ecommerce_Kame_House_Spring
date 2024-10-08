package com.KameHouse.ecom.service.customer.product;

import com.KameHouse.ecom.dto.CategoryDto;
import com.KameHouse.ecom.dto.CompleteProductDetailDto;
import com.KameHouse.ecom.dto.ProductDto;
import com.KameHouse.ecom.entity.Category;
import com.KameHouse.ecom.entity.FAQ;
import com.KameHouse.ecom.entity.Product;
import com.KameHouse.ecom.entity.Reviews;
import com.KameHouse.ecom.repo.CategoryRepository;
import com.KameHouse.ecom.repo.FAQRepository;
import com.KameHouse.ecom.repo.ProductRepository;
import com.KameHouse.ecom.repo.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerProductServiceImpl implements CustomerProductService {

    private final CategoryRepository categoryRepository;

    private final ProductRepository productRepository;

    private final FAQRepository faqRepository;

    private final ReviewRepository reviewRepository;


    @Override
    public List<CategoryDto> getAllCategories() {
        return categoryRepository.findAll().stream().map(Category::getCategoryDto).collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> searchProductByTitle(String title) {
        return productRepository.findAllByNameContaining(title).stream().map(Product::getProductDto).collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> getAllProducts() {
        return productRepository.findAll().stream().map(Product::getProductDto).collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> getProductsByCategory(Long categoryId) {
        return productRepository.findAllByCategoryId(categoryId).stream().map(Product::getProductDto).collect(Collectors.toList());
    }

    @Override
    public CompleteProductDetailDto getCompleteProductDetailById(Long productId) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        List<FAQ> faqList = faqRepository.findAllByProductId(productId);
        List<Reviews> reviewsList = reviewRepository.findAllByProductId(productId);
        if (optionalProduct.isPresent()) {
            CompleteProductDetailDto completeProductDetailDto = new CompleteProductDetailDto();
            completeProductDetailDto.setProductDto(optionalProduct.get().getProductDto());
            completeProductDetailDto.setFaqDtoList(faqList.stream().map(FAQ::getFAQDto).collect(Collectors.toList()));
            completeProductDetailDto.setReviewDtoList(reviewsList.stream().map(Reviews::getReviewDto).collect(Collectors.toList()));
            return completeProductDetailDto;
        }
        return null;
    }

}
