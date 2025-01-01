package com.abhijith.dream_books.dao;

import com.abhijith.dream_books.entity.Cart;
import com.abhijith.dream_books.entity.Product;
import com.abhijith.dream_books.entity.User;

import java.math.BigDecimal;
import java.util.List;

public interface CartDAO {

    void save(Cart theCart);

    void delete(Long id);

    void updateCartItem(Long id, int newQty);

    Cart findById(Long id);

    List<Cart> findAll();

    List<Cart> findCartItemOfUser(User theUser);

    List<Cart> findItemByUserAndProduct(User theUser, Product theProduct);
}
