package com.abhijith.dream_books.controller;

import com.abhijith.dream_books.dao.categoryDao;
import com.abhijith.dream_books.entity.category;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

//  controller for managing homepage
//  pages -> index
@Controller
public class HomeController {

    private final categoryDao theCategoryDao;

    public HomeController(categoryDao theCategoryDao){
        this.theCategoryDao = theCategoryDao;
    }

    @GetMapping("/")
    public String home(Model theModel){

        List<category> theCategories = theCategoryDao.findAll();
        theModel.addAttribute("categories",theCategories);

        // returns index.html
        return "index";
    }

}
