package com.marketprice.marketprice.service;

import com.marketprice.marketprice.entity.ProductDAO;
import com.marketprice.marketprice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService implements IProductService{
    @Autowired
    ProductRepository productRepository;

    public List<ProductDAO> findByName(String name){
        return productRepository.findByName(name);
    }

    public ProductDAO findById(Integer id){
        return productRepository.findById(id).get();
    }

    public List<ProductDAO> searchByName(String name){
        return productRepository.findByNameContainingIgnoreCase(name);
    }
}
