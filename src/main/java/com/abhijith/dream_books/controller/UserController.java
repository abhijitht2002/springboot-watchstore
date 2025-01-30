package com.abhijith.dream_books.controller;

import com.abhijith.dream_books.entity.Role;
import com.abhijith.dream_books.entity.User;
import com.abhijith.dream_books.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    private UserService theUserService;

    public UserController(UserService theUserService){
        this.theUserService = theUserService;

    }

    @GetMapping("/User-dashboard")
    public String yourAccountPage(){
        return "users-dashboard-page";
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
}
