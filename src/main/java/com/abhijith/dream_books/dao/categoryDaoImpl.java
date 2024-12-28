package com.abhijith.dream_books.dao;

import com.abhijith.dream_books.entity.category;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class categoryDaoImpl implements categoryDao{

//  defining a field for entity manager
    private EntityManager theEntityManager;

//    constructor injection ... injecting entity manager
    @Autowired
    public categoryDaoImpl(EntityManager theEntityManager) {
        this.theEntityManager = theEntityManager;
    }

    @Override
    @Transactional
    public void save(category theCategory) {
        theEntityManager.persist(theCategory);
    }

    @Override
    public List<category> findAll() {

        TypedQuery<category> theQuery = theEntityManager.createQuery("FROM category ORDER BY category_id",category.class);
        return theQuery.getResultList();
    }

    @Override
    @Transactional
    public void saveAll(List<category> theCategory) {

        for (category tempCategory : theCategory){
            theEntityManager.persist(tempCategory);
        }
    }
}
