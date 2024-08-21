package com.KameHouse.ecom.services.admin.category;


import com.KameHouse.ecom.dto.CategoryDto;
import com.KameHouse.ecom.entity.Category;
import com.KameHouse.ecom.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {


    private final CategoryRepository categoryRepository;


    public Category createcategory(CategoryDto categoryDto){
        Category category = new Category();
        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());


        return categoryRepository.save(category);

    }




}
