package com.abhijith.dream_books.dao;

import com.abhijith.dream_books.entity.Cart;
import com.abhijith.dream_books.entity.User;

import java.math.BigDecimal;
import java.util.List;

public interface CartDAO {

    void save(Cart theCart);

    List<Cart> findAll();

    List<Cart> findCartItemOfUser(User theUser);
}
