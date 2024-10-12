package com.KameHouse.ecom.repo;

import com.KameHouse.ecom.entity.CartItems;
import com.KameHouse.ecom.entity.CartItemsProducts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemsProductsRepository extends JpaRepository<CartItemsProducts, Long> {

}
