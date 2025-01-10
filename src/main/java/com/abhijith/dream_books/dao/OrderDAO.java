package com.abhijith.dream_books.dao;

import com.abhijith.dream_books.entity.Orders;

public interface OrderDAO{

    void save(Orders orders);

    Orders findOrderById(Long id);
}
