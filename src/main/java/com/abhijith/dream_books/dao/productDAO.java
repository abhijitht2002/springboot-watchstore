package com.abhijith.dream_books.dao;

import com.abhijith.dream_books.entity.product;

import java.util.List;

public interface productDAO {

    void save(product theproduct);

    List<product> findAll();

    List<product> findByGender(String gender);

    product findById(Long id);
}
