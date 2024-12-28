package com.abhijith.dream_books.dao;

import com.abhijith.dream_books.entity.Cart;
import com.abhijith.dream_books.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CartDaoImpl implements CartDAO{

    private EntityManager theEntityManager;

    public CartDaoImpl(EntityManager theEntityManager) {
        this.theEntityManager = theEntityManager;
    }

    @Override
    @Transactional
    public void save(Cart theCart) {

        theEntityManager.persist(theCart);
    }

    @Override
    public List<Cart> findAll() {

        TypedQuery<Cart> theQuery = theEntityManager.createQuery("FROM Cart ORDER BY id", Cart.class);

        return theQuery.getResultList();
    }

    @Override
    public List<Cart> findCartItemOfUser(User theUser) {

        TypedQuery<Cart> theQuery = theEntityManager.createQuery("FROM Cart WHERE theUser = :theUser", Cart.class);
        theQuery.setParameter("theUser", theUser);
        return theQuery.getResultList();
    }
}
