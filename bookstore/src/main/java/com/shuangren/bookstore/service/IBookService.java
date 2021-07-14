package com.shuangren.bookstore.service;

import com.shuangren.bookstore.model.Book;

public interface IBookService  {
    Book getBookTree();
    Book getBookTree2();
    Long getBookPrice(String code);
    Book getMybatisBookTree();
    void updateBook();
}
