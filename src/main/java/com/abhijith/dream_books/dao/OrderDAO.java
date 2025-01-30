package com.abhijith.dream_books.dao;

import com.abhijith.dream_books.entity.Orders;

public interface OrderDAO{

    void save(Orders orders);

    void update(Orders orders);

    void deleteAll();

    Orders findOrderById(Long id);
}
