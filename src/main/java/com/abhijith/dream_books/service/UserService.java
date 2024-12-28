package com.abhijith.dream_books.service;

import com.abhijith.dream_books.entity.Role;
import com.abhijith.dream_books.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {

    User registerNewUser(String username, String password, String email, Role role);

    User UserLogin(String username, String password);

    public int getAuthenticatedUserId();

    public String getAuthenticatedUsername();
}
