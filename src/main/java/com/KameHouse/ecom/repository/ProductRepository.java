package com.KameHouse.ecom.repository;


import com.KameHouse.ecom.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;





@Repository
public interface ProductRepository extends JpaRepository< Product, Long> {

}
