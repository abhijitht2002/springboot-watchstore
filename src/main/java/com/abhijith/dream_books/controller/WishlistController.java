package com.abhijith.dream_books.controller;

import com.abhijith.dream_books.dao.UserDAO;
import com.abhijith.dream_books.dao.WishlistDAO;
import com.abhijith.dream_books.dao.productDAO;
import com.abhijith.dream_books.entity.Product;
import com.abhijith.dream_books.entity.User;
import com.abhijith.dream_books.entity.Wishlist;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class WishlistController {

    private UserDAO theUserDAO;
    private WishlistDAO theWishlistDAO;
    private productDAO theProductDAO;

    public WishlistController(UserDAO theUserDAO, WishlistDAO theWishlistDAO, productDAO theProductDAO){
        this.theUserDAO = theUserDAO;
        this.theWishlistDAO = theWishlistDAO;
        this.theProductDAO = theProductDAO;
    }

    @GetMapping("/wishlist-page")
    public String goToWishlistPage(Model theModel){

        // getting the global attribute username and casting it to string
        String username = (String) theModel.getAttribute("username");
        //  go back to login if username is null
        if(username == null){

            return "redirect:/login";
        }
        // getting the user details with username
        User theUser = theUserDAO.findByUsername(username);
        //  retrieve list of wishlist item with the user details
        List<Wishlist> wishlistItems = theWishlistDAO.findWishlistItemOfUser(theUser);
        // check if the list wishlistItems is not empty and null
        if(wishlistItems != null && !wishlistItems.isEmpty()){

            //  passing in the retrieved wishlist items to attribute wishlistItems
            theModel.addAttribute("wishlistItems", wishlistItems);
        }else {

            System.out.println("no items found");
        }

        //  return to wishlist page
        return "wishlist-page";
    }

    @GetMapping("/wishlist/add")
    public String addToWishlistPage(@RequestParam Long id,
                                    @RequestParam String sourcePage,
                                    Model theModel){

        String username = (String) theModel.getAttribute("username");
        User theUser = theUserDAO.findByUsername(username);

        if(username != null){

            //  find product details by id
            Product theProduct = theProductDAO.findById(id);
            // Check if the item is already in the wishlist
            List<Wishlist> theWishlistExists = theWishlistDAO.findItemByUserAndProduct(theUser, theProduct);
            if(theWishlistExists == null || theWishlistExists.isEmpty()){

                Wishlist newWishlistItem = new Wishlist(theUser, theProduct, LocalDateTime.now());
                theWishlistDAO.save(newWishlistItem);
                System.out.println("item successfully added to wishlist");
            }else {

                System.out.println("item already exists");
            }

        }else {

            return "redirect:/login";
        }

        if("shop".equals(sourcePage)){

            return "redirect:/shop";
        } else if ("home".equals(sourcePage)) {

            return "redirect:/";
        }

        return "redirect:/";
    }

    @GetMapping("/wishlist/remove")
    public String removeWishlistItem(@RequestParam Long id){

        theWishlistDAO.delete(id);

        System.out.println("item successfully deleted!");

        return "redirect:/wishlist-page";
    }
}
