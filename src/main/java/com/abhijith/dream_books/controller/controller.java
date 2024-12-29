package com.abhijith.dream_books.controller;

import com.abhijith.dream_books.dao.CartDAO;
import com.abhijith.dream_books.dao.UserDAO;
import com.abhijith.dream_books.dao.categoryDao;
import com.abhijith.dream_books.dao.productDAO;
import com.abhijith.dream_books.entity.*;
import com.abhijith.dream_books.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class controller {

    private category theCategory;
    private categoryDao theCategoryDao;
    private productDAO theProductDAO;
    private CartDAO cartDAO;
    private UserDAO theUserDAO;
    private productImages theProductImages;
    private Product theProduct;
    private UserService theUserService;

    @Autowired
    public controller(categoryDao theCategoryDao, productDAO theProductDAO, CartDAO theCartDAO, UserDAO theUserDAO, UserService theUserService) {
        this.theCategoryDao = theCategoryDao;
        this.theProductDAO = theProductDAO;
        this.cartDAO = theCartDAO;
        this.theUserDAO = theUserDAO;
        this.theUserService = theUserService;
    }

    @GetMapping("/")
    public String home(Model theModel){
        List<category> theCategories = theCategoryDao.findAll();

        theModel.addAttribute("categories",theCategories);

        return "index";
    }

    @GetMapping("/shop")
    public String showShoppingPage(
            Model theModel,
            @RequestParam(value = "gender", required = false)String gender
    ) {

        System.out.println(gender);

//        List<Product> theProducts;

        List<Product> theProducts = new ArrayList<>();

        if(gender == null){
            theProducts = theProductDAO.findAll();
        }else if(gender.equals("men")){
            theProducts = theProductDAO.findByGender(gender);
        }
        System.out.println(theProducts);
        System.out.println(theProducts.stream().count());
        Long count = theProducts.stream().count();
        theModel.addAttribute("products", theProducts);
        theModel.addAttribute("counts", count);
        return "shopping-page";
    }

    @GetMapping("/shop/filter")
    public String showFilteredShoppingPage(
            Model theModel,
            @RequestParam(value = "gender", required = false)String gender
    ) {

        System.out.println(gender);

//        List<Product> theProducts;

        List<Product> theProducts = new ArrayList<>();

        if(gender == null){
            theProducts = theProductDAO.findAll();
        }else if(gender.equals("men")){
            theProducts = theProductDAO.findByGender(gender);
        }
        System.out.println(theProducts);
        System.out.println(theProducts.stream().count());
        Long count = theProducts.stream().count();
        theModel.addAttribute("products", theProducts);
        theModel.addAttribute("counts", count);
        return "shopping-page";
    }

    @GetMapping("/Product")
    public String showProduct(
            @RequestParam("id") Long id,
            Model theModel
    ) throws JsonProcessingException {

        Product theProd = theProductDAO.findById(id);

        //  Parse specifications JSON into Map
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> specs = mapper.readValue(theProd.getProduct_specifications(), new TypeReference<>() {});

        //  dynamically building a line of details using components inside JSON file ...    stringbuilder append
        //  taking out each details one by one
        String dialcol = specs.get("dial_colour");
        String material = specs.get("material");
        String movement = specs.get("movement");
        String clasp = specs.get("buckle");
        String dd = specs.get("dayanddate");
        String chrono = specs.get("chronograph");
        String strapMaterial = specs.get("strap material");
        String strapCol = specs.get("strap colour");

        StringBuilder details = new StringBuilder();
        details
                .append(theProd.getProduct_brand())
                .append(" " + theProd.getProduct_name())
                .append(" with ")
                .append(dialcol + " dial ")
                .append(" and ")
                .append(strapCol + " " + strapMaterial + " straps ");

//        System.out.println(theProduct);
//        System.out.println("Product with images:" + theProduct.getImagesList().stream().map(productImages::getImage_url).collect(Collectors.toList()));
        theModel.addAttribute("Product", theProd);
        theModel.addAttribute("Eximages", theProd.getImagesList());
        theModel.addAttribute("dydetails", details);
        theModel.addAttribute("prodSpec", specs);
        return "Product-page";
    }

    @GetMapping("/User-dashboard")
    public String yourAccountPage(){
        return "users-dashboard-page";
    }

    @GetMapping("/wishlist-page")
    public String goToWishlistPage(){
        System.out.println("hello world");
        return "wishlist-page";
    }

    @GetMapping("/cart-page")
    public String goToCartPage(Model theModel){

        //  from the Model Attribute the username come here as object so casting very important
        String username = (String) theModel.getAttribute("username");

        if(username != null){

            User theUser = theUserDAO.findByUsername(username);

            List<Cart> theCartItems = cartDAO.findCartItemOfUser(theUser);
            //  check if the cart items are null or empty mostly the list<> retrieves empty list as - [] which must be definitely dealt with .isEmpty()
            if(theCartItems != null && !theCartItems.isEmpty()){

                System.out.println(theCartItems);
                theModel.addAttribute("cartItems", theCartItems);
                return "/cart-page";
            }else {

                System.out.println("no items found");
                return "/cart-page";
            }

        }else {

            return "redirect:/login";
        }

    }

    @PostMapping("/cart/add")
    public String addToCart(@RequestParam("id") Long productId,
                            @RequestParam int quantity,
                            Model theModel,
                            @RequestParam String action){

        System.out.println(action);
        if (action.equals("add-to-cart")){
            String username = theUserService.getAuthenticatedUsername();
            //  check if username is null
            if(username == null){

                return "redirect:/login";
            }
            //  if user not null then find User(entity) by username
            User theUser = theUserDAO.findByUsername(username);

            Product theProduct = theProductDAO.findById(productId);

            System.out.println("productID: " + productId);
            System.out.println("quantity: " + quantity);

            Cart thCart = new Cart( theUser, theProduct, quantity, LocalDateTime.now(), LocalDateTime.now());

            cartDAO.save(thCart);

//            return "redirect:/shop";
        }

        return "redirect:/Product?id=" + productId;
    }

    @GetMapping("/register")
    public String showRegistrationPage(){
        return "/registration";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String username,
                               @RequestParam String password,
                               @RequestParam String email,
                               Model theModel){
        User newUser = theUserService.registerNewUser(username, password, email, Role.ROLE_USER);
        return "redirect:/";   // This redirects to the @GetMapping("/index") method
    }

    @GetMapping("/login")
    public String showLoginPage(){

        return "login";
    }

//    @PostMapping("/login")
//    public String loginUser(@RequestParam String username,
//                            @RequestParam String password,
//                            Model theModel
//                            ){
//        System.out.println("starting login stack...");
//
//        User theUser = theUserService.UserLogin(username, password);
//
//        if (theUser != null){
//
//            System.out.println("username: "+theUser.getUsername()+"\n"+"password: "+theUser.getPassword());
//            theModel.addAttribute("user", theUser);
//            return "redirect:/";
//        }else{
//
//            System.out.println("User not found!");
//            System.out.println(theUser);
//            return "login";
//        }
//    }
}
