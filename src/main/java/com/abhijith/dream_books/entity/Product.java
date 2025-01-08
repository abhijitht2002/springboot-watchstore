package com.abhijith.dream_books.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private int product_id;

    @Column(name = "product_name")
    private String product_name;

    @Column(name = "product_brand")
    private String product_brand;

    @Column(name = "product_gender")
    private String product_gender;

    @Column(name = "product_thumbnail")
    private String product_thumbnail;

    @Column(name = "product_description")
    private String product_description;

    @Column(name = "product_specifications")
    private String product_specifications;

    @Column(name = "product_price")
    private BigDecimal product_price;

    @Column(name = "created_at")
    private LocalDateTime created_at;

//
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<productImages> imagesList = new ArrayList<>();

    public Product(){}

    public Product(String product_name, String product_brand, String product_gender, String product_thumbnail, String product_description, String product_specifications, BigDecimal product_price, LocalDateTime created_at) {
        this.product_name = product_name;
        this.product_brand = product_brand;
        this.product_gender = product_gender;
        this.product_thumbnail = product_thumbnail;
        this.product_description = product_description;
        this.product_specifications = product_specifications;
        this.product_price = product_price;
        this.created_at = created_at;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_brand() {
        return product_brand;
    }

    public void setProduct_brand(String product_brand) {
        this.product_brand = product_brand;
    }

    public String getProduct_gender() {
        return product_gender;
    }

    public void setProduct_gender(String product_gender) {
        this.product_gender = product_gender;
    }

    public String getProduct_thumbnail() {
        return product_thumbnail;
    }

    public void setProduct_thumbnail(String product_thumbnail) {
        this.product_thumbnail = product_thumbnail;
    }

    public String getProduct_description() {
        return product_description;
    }

    public void setProduct_description(String product_description) {
        this.product_description = product_description;
    }

    public String getProduct_specifications() {
        return product_specifications;
    }

    public void setProduct_specifications(String product_specifications) {
        this.product_specifications = product_specifications;
    }

    public BigDecimal getProduct_price() {
        return product_price;
    }

    public void setProduct_price(BigDecimal product_price) {
        this.product_price = product_price;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public List<productImages> getImagesList() {
        return imagesList;
    }

    public void setImagesList(List<productImages> imagesList) {
        this.imagesList = imagesList;
    }

    @Override
    public String toString() {
        return "Product{" +
                "product_id=" + product_id +
                ", product_name='" + product_name + '\'' +
                ", product_brand='" + product_brand + '\'' +
                ", product_gender='" + product_gender + '\'' +
                ", product_thumbnail='" + product_thumbnail + '\'' +
                ", product_description='" + product_description + '\'' +
                ", product_specifications='" + product_specifications + '\'' +
                ", product_price=" + product_price +
                ", created_at=" + created_at +
                '}';
    }

    //    helper methods
    public void addImage(productImages image){
        imagesList.add(image);
        image.setProduct(this);
    }

    public void removeImage(productImages image){
        imagesList.remove(image);
        image.setProduct(null);
    }

}
