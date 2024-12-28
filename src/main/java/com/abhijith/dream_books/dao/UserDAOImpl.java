package com.abhijith.dream_books.dao;

import com.abhijith.dream_books.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

@Repository
public class UserDAOImpl implements UserDAO{

    private EntityManager theEntityManager;

    public UserDAOImpl(EntityManager theEntityManager) {
        this.theEntityManager = theEntityManager;
    }

    @Override
    @Transactional
    public void save(User user) {
        theEntityManager.persist(user);
    }

    @Override
    public User findByUsername(String username) {

        TypedQuery<User> theQuery = theEntityManager.createQuery("FROM User where username = :username", User.class);
        theQuery.setParameter("username", username);

        return theQuery.getSingleResult();
    }

    @Override
    public User findByEmail(String email) {

        TypedQuery<User> theQuery = theEntityManager.createQuery("FROM User WHERE email = :email", User.class);
        theQuery.setParameter("email", email);

        return theQuery.getSingleResult();
    }
}
