package com.KameHouse.ecom.service.admin.category;

import com.KameHouse.ecom.dto.CategoryDto;
import com.KameHouse.ecom.entity.Category;

import java.util.List;

public interface CategoryService {
    Category createCategory(CategoryDto categoryDto);

    List<CategoryDto> getAllCategories();
}
