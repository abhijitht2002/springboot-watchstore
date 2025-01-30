package com.abhijith.dream_books.controller;

import com.abhijith.dream_books.dao.*;
import com.abhijith.dream_books.dto.AddressDTO;
import com.abhijith.dream_books.entity.*;
import com.abhijith.dream_books.service.AddressService;
import com.abhijith.dream_books.service.OrderService;
import com.abhijith.dream_books.service.PaymentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class CartController {

    private CartDAO theCartDAO;
    private UserDAO theUserDAO;
    private productDAO theProductDAO;
    private OrderDAO theOrderDAO;
    private OrderItemDAO theOrderItemDAO;
    private AddressService theAddressService;
    private OrderService theOrderService;
    private PaymentService thePaymentService;

    public CartController(CartDAO theCartDAO, UserDAO theUserDAO, productDAO theProductDAO, OrderDAO theOrderDAO, OrderItemDAO theOrderItemDAO, AddressService theAddressService, OrderService theOrderService, PaymentService thePaymentService) {
        this.theCartDAO = theCartDAO;
        this.theUserDAO = theUserDAO;
        this.theProductDAO = theProductDAO;
        this.theOrderDAO = theOrderDAO;
        this.theOrderItemDAO = theOrderItemDAO;
        this.theAddressService = theAddressService;
        this.theOrderService = theOrderService;
        this.thePaymentService = thePaymentService;
    }

    @GetMapping("/cart-page")
    public String goToCartPage(Model theModel){

        //  from the Model Attribute the username come here as object so casting very important
        String username = (String) theModel.getAttribute("username");

        if(username != null){

            User theUser = theUserDAO.findByUsername(username);

            List<Cart> theCartItems = theCartDAO.findCartItemOfUser(theUser);
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

        List<Cart> cartListExists = theCartDAO.findItemByUserAndProduct(theUser, theProduct);

        System.out.println(action);

        BigDecimal price = theProduct.getProduct_price();
        BigDecimal theQuantity = new BigDecimal(quantity);
        BigDecimal grandtotal = price.multiply(theQuantity);

        if (action.equals("add-to-cart")){

            if(cartListExists == null || cartListExists.isEmpty()){

                Cart thCart = new Cart(theUser, theProduct, quantity, LocalDateTime.now(), LocalDateTime.now(), grandtotal);
                theCartDAO.save(thCart);
                System.out.println("item successfully added to cart");
            }else {

                System.out.println("item already exists");
            }

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

        Cart theCartItem = theCartDAO.findById(id);

        int Quantity = theCartItem.getQuantity();
        System.out.println("qty: " + Quantity);

        int newQuantity = 0;

        if(action.equals("increment")){

            newQuantity = Quantity + 1;
        }else if(action.equals("decrement")){

            newQuantity = Quantity - 1;
        }

        theCartDAO.updateCartItem(id, newQuantity);
        System.out.println("newQty: " + newQuantity);

        return "redirect:/cart-page";
    }

    @GetMapping("/cart/remove")
    public String removeCartItem(@RequestParam Long id){

        theCartDAO.delete(id);

        System.out.println("item removed successfully!");

        return "redirect:/cart-page";
    }

    @PostMapping("/cart/process")
    public String showCartCheckoutPage(@RequestParam String action,
                                       RedirectAttributes redirectAttributes,
                                       Model theModel){

        System.out.println(action);

        String username = (String) theModel.getAttribute("username");

        User theUser = theUserDAO.findByUsername(username);
        List<Cart> cartList = theCartDAO.findCartItemOfUser(theUser);

        Orders newOrder = new Orders();

        newOrder.setUser(theUser);
        newOrder.setSubtotal(theOrderService.calculateCartTotal(cartList));
        newOrder.setOther_charges(new BigDecimal(0));
        newOrder.setTotal(newOrder.getSubtotal().add(newOrder.getOther_charges()));
        newOrder.setOrderStatus(OrderStatus.PENDING);

        theOrderDAO.save(newOrder);

        List<OrderItems> orderItems = theOrderService.addItemsToOrder(theUser, newOrder);

        newOrder.addItems(orderItems);

        theOrderDAO.update(newOrder);

        redirectAttributes.addFlashAttribute("orderId", newOrder.getOrder_id());
        redirectAttributes.addFlashAttribute("action", action);

        return "redirect:/checkout";

    }

    @GetMapping("/checkout")
    public String showCheckoutPage(@ModelAttribute("orderId") Long orderId,
                                   @ModelAttribute("action") String action,
                                   Model theModel){

        Orders theOrder = theOrderDAO.findOrderById(orderId);

        List<OrderItems> theOrderItems = theOrderItemDAO.findOrderItemsByOrder(theOrder);

        theModel.addAttribute("order", theOrder);
        theModel.addAttribute("orderItems", theOrderItems);
        theModel.addAttribute("action", action);

        return "checkout";
    }

    @PostMapping("/checkout/process")
    public String proceedToPayment(@RequestParam Long id,
                                   @ModelAttribute AddressDTO addressDTO,
                                   @ModelAttribute("action") String action,
                                   Model theModel,
                                   RedirectAttributes redirectAttributes){

        String username = (String) theModel.getAttribute("username");
        User theUser = theUserDAO.findByUsername(username);

        Orders order = theOrderDAO.findOrderById(id);

        Address address = theAddressService.createAddressFromDTO(addressDTO, order);

        order.addAddress(address);

        order.setUpdated_at(LocalDateTime.now());

        thePaymentService.createPayment(order);

        theOrderDAO.update(order);

        if(action.equals("cartCheckout")){

            theCartDAO.deleteCartOfUser(theUser);
        }

        return "redirect:/payment/success";
    }

    @GetMapping("/payment/success")
    public String showPaymentSuccess(){

        return "payment-success";
    }


}
