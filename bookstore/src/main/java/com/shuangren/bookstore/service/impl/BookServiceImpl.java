package com.shuangren.bookstore.service.impl;

import com.shuangren.bookstore.mapper.BookMapper;
import com.shuangren.bookstore.model.Book;
import com.shuangren.bookstore.service.IBookService;
import com.shuangren.bookstore.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("bookService")
public class BookServiceImpl implements IBookService {
    @Autowired
    private BookMapper bookMapper;
    @Autowired
    private RedisUtil redisUtil;
    @Override
    public Book getBookTree() {
        List<Book> books = bookMapper.selectBooks();
        Map<Long, Book> bookMap = new HashMap<>();
        for (Book book : books) {
            bookMap.put(book.getId(), book);
        }
        Book root = null;
        for (Book book : books) {
            if (book.getParentId() == null) {
                root = book;
            } else {
                Book parent = bookMap.get(book.getParentId());
                if (parent.getChildList() == null) {
                    parent.setChildList(new ArrayList<>());
                }
                parent.getChildList().add(book);
            }
        }
        return root;
    }

    /**
     * 一次for循环构建树形结构
     * @return
     */
    @Override
    public Book getBookTree2() {
        List<Book> books = bookMapper.selectBooks();
        Map<Long, Book> bookMap = new HashMap<>();
        //为还未创建的父节点生成一个临时的子节点集合
        Map<Long, List<Book>> childBookMap = new HashMap<>();
        Book root = null;
        for (Book book : books) {
            List<Book> childs = childBookMap.get(book.getId());
            if (childs != null) {
                book.setChildList(childs);
            }
            if (book.getParentId() == null) {
                root = book;
            } else {
                Book parent = bookMap.get(book.getParentId());
                List<Book> parentChilds;
                if (parent == null) {
                    parentChilds = childBookMap.get(book.getParentId());
                    if (parentChilds == null) {
                        parentChilds = new ArrayList<>();
                        childBookMap.put(book.getParentId(), parentChilds);
                    }
                } else {
                    parentChilds = parent.getChildList();
                    if (parentChilds == null) {
                        parentChilds = new ArrayList<>();
                        parent.setChildList(parentChilds);
                    }
                }
                parentChilds.add(book);
            }
            bookMap.put(book.getId(), book);
        }
        return root;
    }

    @Override
    public Long getBookPrice(String code) {
        return getPrice(bookMapper.selectBook(code));
    }
    private long getPrice(Book book) {
        if (book.getIsBook()) {
            return book.getPrice();
        } else {
            Long price = book.getPrice();
            if (book.getChildList() != null && book.getChildList().size() > 0) {
                for (Book child : book.getChildList()) {
                    price += getPrice(child);
                }
            }
            return price;
        }
    }
    @Override
    public Book getMybatisBookTree() {
        Book book = bookMapper.selectBookTree();
        redisUtil.set("bookTree",book);
        return book;
    }

    @Override
    public void updateBook() {
        Book book = bookMapper.selectBookTree();
        redisUtil.set("bookTree",book);
    }
}
