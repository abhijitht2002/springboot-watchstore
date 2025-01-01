package com.abhijith.dream_books.dao;

import com.abhijith.dream_books.entity.Product;
import com.abhijith.dream_books.entity.User;
import com.abhijith.dream_books.entity.Wishlist;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class WishlistDaoImpl implements WishlistDAO{

    private EntityManager theEntityManager;

    public WishlistDaoImpl(EntityManager theEntityManager) {
        this.theEntityManager = theEntityManager;
    }

    @Override
    @Transactional
    public void save(Wishlist wishlist) {

        theEntityManager.persist(wishlist);
    }

    @Override
    @Transactional
    public void delete(Long id) {

        Wishlist theWishlistItem = findById(id);

        theEntityManager.remove(theWishlistItem);
    }

    @Override
    public Wishlist findById(Long id) {

        return theEntityManager.find(Wishlist.class, id);
    }

    @Override
    public List<Wishlist> findWishlistItemOfUser(User theUser) {

        TypedQuery<Wishlist> theQuery = theEntityManager.createQuery("FROM Wishlist WHERE user = :user", Wishlist.class);
        theQuery.setParameter("user", theUser);
        return theQuery.getResultList();
    }

    @Override
    public List<Wishlist> findItemByUserAndProduct(User theUser, Product theProduct) {

        TypedQuery<Wishlist> theQuery = theEntityManager.createQuery("FROM Wishlist WHERE user = :user AND product = :product", Wishlist.class);
        theQuery.setParameter("user", theUser);
        theQuery.setParameter("product", theProduct);

        return theQuery.getResultList();
    }
}
