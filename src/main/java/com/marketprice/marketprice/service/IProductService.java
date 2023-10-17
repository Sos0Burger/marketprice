package com.marketprice.marketprice.service;

import com.marketprice.marketprice.entity.ProductDAO;

import java.util.List;

public interface IProductService {
    List<ProductDAO> findByName(String name);

    ProductDAO findById(Integer id);

    List<ProductDAO> searchByName(String name);
}
