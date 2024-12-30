package com.abhijith.dream_books.dao;

import com.abhijith.dream_books.entity.Product;

import java.math.BigDecimal;
import java.util.List;

public interface productDAO {

    void save(Product theproduct);

    List<Product> findAll();

    List<Product> findByGender(String gender);

    Product findById(Long id);

    BigDecimal findPriceById(Long id);
}
