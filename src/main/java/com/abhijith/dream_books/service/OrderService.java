package com.abhijith.dream_books.service;

import com.abhijith.dream_books.dao.CartDAO;
import com.abhijith.dream_books.dao.OrderDAO;
import com.abhijith.dream_books.dao.OrderItemDAO;
import com.abhijith.dream_books.entity.Cart;
import com.abhijith.dream_books.entity.OrderItems;
import com.abhijith.dream_books.entity.Orders;
import com.abhijith.dream_books.entity.User;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    private CartDAO theCartDAO;

    private OrderDAO theOrderDAO;

    private OrderItemDAO theOrderItemDAO;

    public OrderService(CartDAO theCartDAO, OrderDAO theOrderDAO, OrderItemDAO theOrderItemDAO) {
        this.theCartDAO = theCartDAO;
        this.theOrderDAO = theOrderDAO;
        this.theOrderItemDAO = theOrderItemDAO;
    }

    public List<OrderItems> addItemsToOrder(User theUser, Orders order){

        // Retrieve all cart items for the user
        List<Cart> cartList = theCartDAO.findCartItemOfUser(theUser);

        // Create a list to hold the generated OrderItems
        List<OrderItems> orderItemsList = new ArrayList<>();

        // Loop through the cart items and create corresponding order items
        for (Cart cart : cartList){

            OrderItems orderItems = new OrderItems();
            orderItems.setOrders(order);
            orderItems.setProduct(cart.getTheProduct());
            orderItems.setPrice(cart.getTheProduct().getProduct_price());
            orderItems.setQuantity(cart.getQuantity());
            orderItems.setTotal(cart.getTotal_price());

            // Save the order item
            theOrderItemDAO.save(orderItems);
            //  add it to the list orderItemsList
            orderItemsList.add(orderItems);
        }

        return orderItemsList;
    }

    public BigDecimal calculateCartTotal(List<Cart> cartList){

//        BigDecimal total = new BigDecimal(0);
        BigDecimal total = BigDecimal.ZERO;     // it is similar to => int total = 0;

        for (Cart cart : cartList){

            total = total.add(cart.getTotal_price());
        }

        return total;
    }
}
