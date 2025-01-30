package com.abhijith.dream_books.dao;

import com.abhijith.dream_books.entity.Cart;
import com.abhijith.dream_books.entity.Product;
import com.abhijith.dream_books.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class CartDaoImpl implements CartDAO{

    private EntityManager theEntityManager;

    public CartDaoImpl(EntityManager theEntityManager) {
        this.theEntityManager = theEntityManager;
    }

    @Override
    @Transactional
    public void save(Cart theCart) {

        theEntityManager.persist(theCart);
    }

    @Override
    @Transactional
    public void delete(Long id) {

        Cart theCart = findById(id);

        theEntityManager.remove(theCart);
    }

    @Override
    @Transactional
    public void deleteCartOfUser(User theUser) {

        theEntityManager.createQuery("DELETE FROM Cart WHERE theUser=:theUser")
                .setParameter("theUser", theUser)
                .executeUpdate();
    }

    @Override
    @Transactional
    public void updateCartItem(Long id, int newQty) {

        Cart theCartItem = findById(id);

        //  update the new quantity
        theCartItem.setQuantity(newQty);
        //  update the new date-time stamp to updated_at
        theCartItem.setUpdated_at(LocalDateTime.now());
        //  update the new total price calculating (price * newQty)
        theCartItem.setTotal_price(theCartItem.getTheProduct().getProduct_price().multiply(new BigDecimal(newQty)));

        theEntityManager.merge(theCartItem);
    }

    @Override
    public Cart findById(Long id) {

        return theEntityManager.find(Cart.class, id);
    }

    @Override
    public List<Cart> findAll() {

        TypedQuery<Cart> theQuery = theEntityManager.createQuery("FROM Cart ORDER BY id", Cart.class);

        return theQuery.getResultList();
    }

    @Override
    public List<Cart> findCartItemOfUser(User theUser) {

        TypedQuery<Cart> theQuery = theEntityManager.createQuery("FROM Cart WHERE theUser = :theUser", Cart.class);
        theQuery.setParameter("theUser", theUser);
        return theQuery.getResultList();
    }

    @Override
    public List<Cart> findItemByUserAndProduct(User theUser, Product theProduct) {

        TypedQuery<Cart> theQuery = theEntityManager.createQuery("FROM Cart WHERE theUser=:theUser AND theProduct=:theProduct", Cart.class);
        theQuery.setParameter("theUser", theUser);
        theQuery.setParameter("theProduct", theProduct);

        return theQuery.getResultList();
    }
}
