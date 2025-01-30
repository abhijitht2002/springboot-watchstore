package com.abhijith.dream_books.dao;

import com.abhijith.dream_books.entity.OrderItems;
import com.abhijith.dream_books.entity.Orders;

import java.util.List;

public interface OrderItemDAO {

    void save(OrderItems orderItems);

    OrderItems findOrderItemByOrder(Orders theOrder);

    List<OrderItems> findOrderItemsByOrder(Orders theOrder);
}
