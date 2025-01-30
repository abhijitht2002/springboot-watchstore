package com.abhijith.dream_books.dao;

import com.abhijith.dream_books.entity.OrderItems;
import com.abhijith.dream_books.entity.Orders;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrderItemDaoImpl implements OrderItemDAO{

    private EntityManager theEntityManager;

    public OrderItemDaoImpl(EntityManager theEntityManager) {
        this.theEntityManager = theEntityManager;
    }

    @Override
    @Transactional
    public void save(OrderItems orderItems) {
        theEntityManager.persist(orderItems);
    }

    @Override
    public OrderItems findOrderItemByOrder(Orders theOrder) {

        TypedQuery<OrderItems> theQuery = theEntityManager.createQuery("FROM OrderItems WHERE orders=:theOrder", OrderItems.class);
        theQuery.setParameter("theOrder", theOrder);

        return theQuery.getSingleResult();
    }

    @Override
    public List<OrderItems> findOrderItemsByOrder(Orders theOrder) {

        TypedQuery<OrderItems> theQuery = theEntityManager.createQuery("FROM OrderItems WHERE orders=:theOrder", OrderItems.class);
        theQuery.setParameter("theOrder", theOrder);

        return theQuery.getResultList();
    }
}
