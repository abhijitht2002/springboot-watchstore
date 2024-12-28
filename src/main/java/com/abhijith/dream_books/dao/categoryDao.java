package com.abhijith.dream_books.dao;

import com.abhijith.dream_books.entity.category;

import java.util.List;

public interface categoryDao {

    //    saving a single line of data as the method executes
    void save(category theCategory);

    //      find all the data from the table ... exactly like select*from table
    List<category> findAll();

    //      insert multiple rows of data as the method executes
    void saveAll(List<category> theCategory);
}
