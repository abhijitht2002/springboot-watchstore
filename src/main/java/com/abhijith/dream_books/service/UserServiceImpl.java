package com.abhijith.dream_books.service;

import com.abhijith.dream_books.dao.UserDAO;
import com.abhijith.dream_books.entity.Role;
import com.abhijith.dream_books.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService{

    private UserDAO theUserDAO;

    private BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(UserDAO theUserDAO, BCryptPasswordEncoder passwordEncoder) {
        this.theUserDAO = theUserDAO;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User registerNewUser(String username, String password, String email, Role role) {

        String encodedPassword = passwordEncoder.encode(password);

        User theUser = new User(username,encodedPassword,email,role);

        theUserDAO.save(theUser);
        return theUser;
    }

    @Override
    public User UserLogin(String username, String password) {

        User theUser = theUserDAO.findByUsername(username);

        if(passwordEncoder.matches(password, theUser.getPassword())){

            return theUser;
        }

        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User theUser = theUserDAO.findByUsername(username);

        if(theUser == null){

            throw new UsernameNotFoundException("user not found: " + username);
        }

        // Convert the user's role (enum) to an authority
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(theUser.getRole().name());

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(authority);

        return new org.springframework.security.core.userdetails.User(
                theUser.getUsername(),
                theUser.getPassword(),
                authorities
        );
    }

    public Authentication getAuthenticatedUser(){

        return SecurityContextHolder.getContext().getAuthentication();
    }

    @Override
    public int getAuthenticatedUserId() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if(auth != null && auth.isAuthenticated()){

            String username = auth.getName();
        }

        return 0;
    }

    @Override
    public String getAuthenticatedUsername() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if(auth != null && auth.isAuthenticated()){

            String username = auth.getName();
            return username;
        }else {
            return null;
        }

    }
}
