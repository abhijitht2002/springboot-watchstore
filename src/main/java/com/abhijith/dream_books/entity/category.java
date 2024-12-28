package com.abhijith.dream_books.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "category")
public class category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private int category_id;

    @Column(name = "category_title")
    private String category_title;

    @Column(name = "category_image_url")
    private String category_image_url;

    @Column(name = "description")
    private String description;

    public category(){}

    public category(String category_title, String category_image_url, String description) {
        this.category_id = category_id;
        this.category_title = category_title;
        this.category_image_url = category_image_url;
        this.description = description;
    }

    //    setters and getters
    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public String getCategory_title() {
        return category_title;
    }

    public void setCategory_title(String category_title) {
        this.category_title = category_title;
    }

    public String getCategory_image_url() {
        return category_image_url;
    }

    public void setCategory_image_url(String category_image_url) {
        this.category_image_url = category_image_url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "category{" +
                "category_id=" + category_id +
                ", category_title='" + category_title + '\'' +
                ", category_image_url='" + category_image_url + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
