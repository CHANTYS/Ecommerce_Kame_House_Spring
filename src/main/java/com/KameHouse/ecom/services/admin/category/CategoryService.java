package com.KameHouse.ecom.services.admin.category;

import com.KameHouse.ecom.dto.CategoryDto;
import com.KameHouse.ecom.entity.Category;

public interface CategoryService {


    Category createcategory(CategoryDto categoryDto);
    
}
