package com.abhijith.dream_books.controller;

import com.abhijith.dream_books.dao.*;
import com.abhijith.dream_books.dto.AddressDTO;
import com.abhijith.dream_books.entity.*;
import com.abhijith.dream_books.service.AddressService;
import com.abhijith.dream_books.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
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
    private Wishlist wishlist;
    private WishlistDAO theWishlistDAO;
    private OrderDAO theOrderDAO;
    private OrderItemDAO theOrderItemDAO;
    private AddressService theAddressService;

    public controller(categoryDao theCategoryDao, productDAO theProductDAO, CartDAO cartDAO, UserDAO theUserDAO, UserService theUserService, WishlistDAO theWishlistDAO, OrderDAO theOrderDAO, OrderItemDAO theOrderItemDAO, AddressService theAddressService) {
        this.theCategoryDao = theCategoryDao;
        this.theProductDAO = theProductDAO;
        this.cartDAO = cartDAO;
        this.theUserDAO = theUserDAO;
        this.theUserService = theUserService;
        this.theWishlistDAO = theWishlistDAO;
        this.theOrderDAO = theOrderDAO;
        this.theOrderItemDAO = theOrderItemDAO;
        this.theAddressService = theAddressService;
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

//        System.out.println(gender);

//        List<Product> theProducts;

        List<Product> theProducts = new ArrayList<>();

        if(gender == null){
            theProducts = theProductDAO.findAll();
        }else if(gender.equals("men")){
            theProducts = theProductDAO.findByGender(gender);
        }
//        System.out.println(theProducts);
//        System.out.println(theProducts.stream().count());
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

    @GetMapping("/product")
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
        theModel.addAttribute("product", theProd);
        theModel.addAttribute("Eximages", theProd.getImagesList());
        theModel.addAttribute("dydetails", details);
        theModel.addAttribute("prodSpec", specs);
        return "product-page";
    }

    @GetMapping("/User-dashboard")
    public String yourAccountPage(){
        return "users-dashboard-page";
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

    @GetMapping("/cart-page")
    public String goToCartPage(Model theModel){

        //  from the Model Attribute the username come here as object so casting very important
        String username = (String) theModel.getAttribute("username");

        if(username != null){

            User theUser = theUserDAO.findByUsername(username);

            List<Cart> theCartItems = cartDAO.findCartItemOfUser(theUser);
            //  check if the cart items are null or empty mostly the list<> retrieves empty list as - [] which must be definitely dealt with .isEmpty()
            if(theCartItems != null && !theCartItems.isEmpty()){

                theModel.addAttribute("cartItems", theCartItems);
                return "cart-page";
            }else {

                System.out.println("no items found");
                return "cart-page";
            }

        }else {

            return "redirect:/login";
        }
    }

    @PostMapping("/cart/add")
    public String addToCart(@RequestParam("id") Long productId,
                            @RequestParam int quantity,
                            Model theModel,
                            @RequestParam String action,
                            @RequestParam String sourcePage,
                            RedirectAttributes redirectAttributes){

        String username = (String) theModel.getAttribute("username");
        //  check if username is null
        if(username == null){

            return "redirect:/login";
        }
        //  if user not null then find User(entity) by username
        User theUser = theUserDAO.findByUsername(username);

        //  get the product by productId
        Product theProduct = theProductDAO.findById(productId);

        System.out.println(action);

        BigDecimal price = theProduct.getProduct_price();
        BigDecimal theQuantity = new BigDecimal(quantity);
        BigDecimal grandtotal = price.multiply(theQuantity);

        if (action.equals("add-to-cart")){

            Cart thCart = new Cart(theUser, theProduct, quantity, LocalDateTime.now(), LocalDateTime.now(), grandtotal);
            cartDAO.save(thCart);
        }else
        if(action.equals("buy-now")){

            System.out.println(theProduct);
            //  since buy-now so there will be only one product obviously ... saving the order item here itself and initializing a new order and passing the order Id to checkout

            Orders newOrder = new Orders(theUser, grandtotal, new BigDecimal(0), grandtotal, OrderStatus.PENDING);

            OrderItems orderItem = new OrderItems(newOrder, theProduct, theProduct.getProduct_price(), quantity, theProduct.getProduct_price().multiply(theQuantity));

            newOrder.addItem(orderItem);

            theOrderDAO.save(newOrder);

            redirectAttributes.addFlashAttribute("orderId", newOrder.getOrder_id());

            return "redirect:/checkout";

            //  return "redirect:/checkout?id=" + productId;
        }

        if(sourcePage.equals("product")){

            return "redirect:/product?id=" + productId;
        }else
        if(sourcePage.equals("wishlist")) {

            return "redirect:/wishlist-page";
        }else {

            return "redirect:/";
        }
    }

    @PostMapping("/cart/update")
    public String updateCartItem(@RequestParam String action,
                                 @RequestParam Long id){

        Cart theCartItem = cartDAO.findById(id);

        int Quantity = theCartItem.getQuantity();
        System.out.println("qty: " + Quantity);

        int newQuantity = 0;

        if(action.equals("increment")){

            newQuantity = Quantity + 1;
        }else if(action.equals("decrement")){

            newQuantity = Quantity - 1;
        }

        cartDAO.updateCartItem(id, newQuantity);
        System.out.println("newQty: " + newQuantity);

        return "redirect:/cart-page";
    }

    @GetMapping("/cart/remove")
    public String removeCartItem(@RequestParam Long id){

        cartDAO.delete(id);

        System.out.println("item removed successfully!");

        return "redirect:/cart-page";
    }

    @PostMapping("/cart/checkout")
    public String showCartCheckoutPage(Model theModel,
                                   @RequestParam String action){

        String userName = (String) theModel.getAttribute("username");
        User theUser = theUserDAO.findByUsername(userName);

        System.out.println(action);

        return "checkout";
    }

    @GetMapping("/checkout")
    public String showCheckoutPage(@ModelAttribute("orderId") Long orderId,
                                   Model theModel){

        Orders theOrder = theOrderDAO.findOrderById(orderId);
        OrderItems theOrderItem = theOrderItemDAO.findOrderItemByOrder(theOrder);

        theModel.addAttribute("order", theOrder);
        theModel.addAttribute("orderItem", theOrderItem);

        return "checkout";
    }

    @PostMapping("/checkout/process")
    public String proceedToPayment(@RequestParam Long id,
                                   @ModelAttribute AddressDTO addressDTO,
                                   Model theModel,
                                   RedirectAttributes redirectAttributes){

        System.out.println(addressDTO);

        Orders order = theOrderDAO.findOrderById(id);

        Address address = theAddressService.createAddressFromDTO(addressDTO, order);

        order.addAddress(address);

        theOrderDAO.save(order);

        redirectAttributes.addFlashAttribute("order", order.getOrder_id());

        return "redirect:/payment";
    }

    @GetMapping("/payment")
    public String showPaymentPage(@ModelAttribute("order") Long id,
                                  Model theModel){

        Orders theOrder = theOrderDAO.findOrderById(id);

        theModel.addAttribute("order", theOrder);

        return "payment";
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

//    @GetMapping("/logout")
//    public String showLogoutPage(){
//
//        return "logout";
//    }
}
