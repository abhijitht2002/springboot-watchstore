package com.abhijith.dream_books.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "images")
public class productImages {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private int image_id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "image_url")
    private String image_url;

    public  productImages(){}

    public productImages(Product Product, String image_url) {
        this.product = Product;
        this.image_url = image_url;
    }

    public int getImage_id() {
        return image_id;
    }

    public void setImage_id(int image_id) {
        this.image_id = image_id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    @Override
    public String toString() {
        return "productImages{" +
                "image_id=" + image_id +
                ", product_id=" + product +
                ", image_url='" + image_url + '\'' +
                '}';
    }
}
