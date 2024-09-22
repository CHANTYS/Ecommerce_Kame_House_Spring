package com.KameHouse.ecom.controller.admin;


import com.KameHouse.ecom.dto.CategoryDto;
import com.KameHouse.ecom.entity.Category;
import com.KameHouse.ecom.services.admin.category.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor


public class AdminCategoryController {


    private final CategoryService categoryService;


    @PostMapping("category")

    public ResponseEntity<Category> createCategory(@RequestBody CategoryDto categoryDto){

        Category category = categoryService.createcategory(categoryDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(category);

    }

    @GetMapping("category")
    public ResponseEntity<List<Category>> getAllCategories(){
        return ResponseEntity.ok(categoryService.getAllCategories());
    }
}
