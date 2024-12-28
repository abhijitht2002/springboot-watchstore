package com.abhijith.dream_books.dao;

import com.abhijith.dream_books.entity.User;

public interface UserDAO {

    void save(User user);
    User findByUsername(String username);
    User findByEmail(String email);
}
