package com.shuangren.bookstore.model;

import lombok.Data;

import java.util.List;

@Data
public class Book {
    private Long id;

    private String code;

    private Long parentId;

    private Long price;

    private Boolean isBook;
    private List<Book> childList;
    public Book(){}
    public Book(Long id, Long parentId, String code, Long price, boolean isBook) {
        this.id = id;
        this.parentId = parentId;
        this.code = code;
        this.price = price;
        this.isBook = isBook;
    }
}