package com.abhijith.dream_books.dao;

import com.abhijith.dream_books.entity.Orders;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

@Repository
public class OrderDaoImpl implements OrderDAO{

    private EntityManager theEntityManager;

    public OrderDaoImpl(EntityManager theEntityManager) {
        this.theEntityManager = theEntityManager;
    }

    @Override
    @Transactional
    public void save(Orders orders) {
        theEntityManager.persist(orders);
    }

    @Override
    @Transactional
    public void update(Orders orders) {
        theEntityManager.merge(orders);
    }

    @Override
    @Transactional
    public void deleteAll() {
        theEntityManager.createQuery("DELETE FROM Orders").executeUpdate();
    }

    @Override
    public Orders findOrderById(Long id) {
        return theEntityManager.find(Orders.class, id);
    }
}
