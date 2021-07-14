package com.shuangren.bookstore.controller;

import com.shuangren.bookstore.model.Book;
import com.shuangren.bookstore.service.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/book")
public class BookController {
    @Autowired
    @Qualifier("bookService")
    private IBookService bookService;

    @RequestMapping("/getBookTree")
    public Book getBookTree() {
        return bookService.getBookTree();
    }

    @RequestMapping("/getBookTree2")
    public Book getBookTree2() {
        return bookService.getBookTree2();
    }

    @RequestMapping("/getBookPrice")
    public Long getBookPrice(@RequestParam("code") String code) {
        return bookService.getBookPrice(code);
    }

    @RequestMapping("/getMybatisBookTree")
    public Book getMybatisBookTree() {
        return bookService.getMybatisBookTree();
    }

    @RequestMapping("/updateBook")
    public void updateBook() {
        bookService.updateBook();
    }
}
