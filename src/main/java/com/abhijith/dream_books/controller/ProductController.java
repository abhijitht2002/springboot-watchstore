package com.abhijith.dream_books.controller;

import com.abhijith.dream_books.dao.productDAO;
import com.abhijith.dream_books.entity.Product;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class ProductController {

    private final productDAO theProductDAO;

    public ProductController(productDAO theProductDAO){
        this.theProductDAO = theProductDAO;
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
}
