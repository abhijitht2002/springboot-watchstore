package com.abhijith.dream_books.service;

import com.abhijith.dream_books.dao.OrderDAO;
import com.abhijith.dream_books.entity.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PaymentService {

    private OrderDAO theOrderDAO;

    public PaymentService(OrderDAO theOrderDAO) {
        this.theOrderDAO = theOrderDAO;
    }

    public void createCodPayment(Long id){

        Orders theOrders = theOrderDAO.findOrderById(id);

        Payment thePayment = new Payment(theOrders, PaymentMode.COD, PaymentStatus.PENDING, theOrders.getTotal());

        theOrders.setPayment(thePayment);

        theOrders.setUpdated_at(LocalDateTime.now());

        theOrders.setOrderStatus(OrderStatus.PLACED);

        theOrderDAO.update(theOrders);

    }
}
