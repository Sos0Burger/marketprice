package com.marketprice.marketprice.repository;

import com.marketprice.marketprice.entity.ProductDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductDAO, Integer> {
    List<ProductDAO> findByName(String name);

    List<ProductDAO> findByNameContainingIgnoreCase(String name);
}
