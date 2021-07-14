package com.shuangren.bookstore;

import com.shuangren.bookstore.mapper.BookMapper;
import com.shuangren.bookstore.model.Book;
import com.shuangren.bookstore.utils.JsonUtils;
import com.shuangren.bookstore.utils.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 1.设计一个对象代表描述上述模型  => Book类
 * 2.将数据库表数据全部加载进入内存，然后构建成一个树形结构，入参可以写成MOCK一个List，不用实际读取数据库  testload1()：两次for循环   testload2()一次for循环
 * 3 价格计算   testPrice()手动调整Book再计算   testPrice2()从数据库获取树形结构计算价格
 * 4 testMybatisTree()获取整颗树
 */
@SpringBootTest
class BookstoreApplicationTests {
    @Autowired
    private BookMapper bookMapper;
    @Autowired
    private RedisUtil redisUtil;

    @Test
    void contextLoads() {
    }

    @Test
    public void testInserts() {
        List<Book> books = new ArrayList<>();
        books.add(new Book(1L, 5L, "高等数学", 10L, true));
        books.add(new Book(2L, 5L, "线性代数", 20L, true));
        books.add(new Book(3L, 6L, "唐诗", 15L, true));
        books.add(new Book(4L, 6L, "宋词", 14L, true));
        books.add(new Book(5L, 7L, "数学类", -3L, false));
        books.add(new Book(6L, 7L, "语文类", -2L, false));
        books.add(new Book(7L, null, "教材类", -5L, false));
        bookMapper.inserts(books);
    }

    /**
     * 构建成一个树形结构
     */
    @Test
    public void testload1() {
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
        if (root != null) {
            System.out.println(JsonUtils.objectToJson(root));
        }
    }

    /**
     * 构建成一个树形结构 方式2  直接构建： 通过提前为父节点生成childList可以减少一次for循环
     */
    @Test
    public void testload2() {
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
        if (root != null) {
            System.out.println(JsonUtils.objectToJson(root));
        }
    }

    /**
     * 手动组装Book计算价格
     */
    @Test
    public void testPrice() {
        List<Book> books = bookMapper.selectBooks();
        Map<Long, Book> bookMap = new HashMap<>();
        for (Book book : books) {
            bookMap.put(book.getId(), book);
        }
        for (Book book : books) {
            if (book.getParentId() != null) {
                Book parent = bookMap.get(book.getParentId());
                if (parent.getChildList() == null) {
                    parent.setChildList(new ArrayList<>());
                }
                parent.getChildList().add(book);
            }
        }
        System.out.println(getPrirce(books, "教材类"));
        System.out.println(getPrirce(books, "语文类"));
        System.out.println(getPrirce(books, "数学类"));
        System.out.println(getPrirce(books, "高等数学"));
    }

    /**
     * 通过mysql获取树形结构计算价格
     */
    @Test
    public void testPrice2(){
        System.out.println(getPrice(bookMapper.selectBook("教材类")));
        System.out.println(getPrice(bookMapper.selectBook("语文类")));
        System.out.println(getPrice(bookMapper.selectBook("数学类")));
        System.out.println(getPrice(bookMapper.selectBook("高等数学")));
    }
    private long getPrirce(List<Book> books, String name) {
        for (Book book : books) {
            if (book.getCode().equals(name)) {
                return getPrice(book);
            }
        }
        return 0;
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

    /**
     * 2.查询出树结构的数据使用mybatis 的<collection>标签递归查询出整个树结构 存入redis缓存中 更新数据时同时也更新redis缓存
     */
    @Test
    public void testMybatisTree(){
        Book book = bookMapper.selectBookTree();
        redisUtil.set("bookTree",book);
        System.out.println(JsonUtils.objectToJson(book));
    }
}
