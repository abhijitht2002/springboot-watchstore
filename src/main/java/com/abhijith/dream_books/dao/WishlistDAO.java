package com.abhijith.dream_books.dao;

import com.abhijith.dream_books.entity.Product;
import com.abhijith.dream_books.entity.User;
import com.abhijith.dream_books.entity.Wishlist;

import java.util.List;

public interface WishlistDAO {

    void save(Wishlist wishlist);

    void delete(Long id);

    Wishlist findById(Long id);

    List<Wishlist> findWishlistItemOfUser(User theUser);

    List<Wishlist> findItemByUserAndProduct(User theUser, Product theProduct);
}
