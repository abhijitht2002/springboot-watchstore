package com.abhijith.dream_books.dao;


import com.abhijith.dream_books.entity.product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class productDaoImpl implements productDAO{

    private EntityManager theEntityManager;

    public productDaoImpl(EntityManager theEntityManager) {
        this.theEntityManager = theEntityManager;
    }

    @Override
    @Transactional
    public void save(product theproduct) {
        theEntityManager.persist(theproduct);
    }

    @Override
    public List<product> findAll() {

        TypedQuery<product> theQuery = theEntityManager.createQuery("FROM product ORDER BY product_id", product.class);

        return theQuery.getResultList();
    }

    @Override
    public List<product> findByGender(String gender) {

        TypedQuery<product> theQuery = theEntityManager.createQuery("FROM product WHERE product_gender = :gender", product.class);
        theQuery.setParameter("gender", gender);    /* set the gender parameter in the query */
        return theQuery.getResultList();
    }

    @Override
    public product findById(Long id) {

        return theEntityManager.find(product.class, id);
    }
}
