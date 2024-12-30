package com.abhijith.dream_books.dao;


import com.abhijith.dream_books.entity.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public class productDaoImpl implements productDAO{

    private EntityManager theEntityManager;

    public productDaoImpl(EntityManager theEntityManager) {
        this.theEntityManager = theEntityManager;
    }

    @Override
    @Transactional
    public void save(Product theproduct) {
        theEntityManager.persist(theproduct);
    }

    @Override
    public List<Product> findAll() {

        TypedQuery<Product> theQuery = theEntityManager.createQuery("FROM Product ORDER BY product_id", Product.class);

        return theQuery.getResultList();
    }

    @Override
    public List<Product> findByGender(String gender) {

        TypedQuery<Product> theQuery = theEntityManager.createQuery("FROM Product WHERE product_gender = :gender", Product.class);
        theQuery.setParameter("gender", gender);    /* set the gender parameter in the query */
        return theQuery.getResultList();
    }

    @Override
    public Product findById(Long id) {

        return theEntityManager.find(Product.class, id);
    }

    @Override
    public BigDecimal findPriceById(Long id) {

        Product theProduct = findById(id);

        return theProduct.getProduct_price();
    }
}
