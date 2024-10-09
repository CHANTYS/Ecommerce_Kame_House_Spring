package com.KameHouse.ecom.repo;

import com.KameHouse.ecom.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    List<Product> findAllByNameContaining(String title);

    List<Product> findAllByCategoryId(Long categoryId);


}