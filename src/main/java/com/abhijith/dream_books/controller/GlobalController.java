package com.abhijith.dream_books.controller;

import com.abhijith.dream_books.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalController {

    UserService theUserService;

    public GlobalController(UserService theUserService) {
        this.theUserService = theUserService;
    }

    @ModelAttribute("username")
    public String getLoggedInUsername(){

        String username = theUserService.getAuthenticatedUsername();
        //  since the spring security sees a null user as "anonymousUser" so we must look into that ... simple if condition to make the user null if username is "anonymousUser" else the username itself
        return username.equals("anonymousUser") ? null : username;
    }
}
