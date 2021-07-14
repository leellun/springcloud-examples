package com.shuangren.bookstore.mapper;

import com.shuangren.bookstore.model.Book;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookMapper {
    /**
     * 测试插入数据
     * @param books
     */
    public void inserts(@Param("books") List<Book> books);

    /**
     * 获取所有数据
     * @return
     */
    public List<Book> selectBooks();

    /**
     * 获取正棵树
     * @return
     */
    public Book selectBookTree();

    /**
     * 获取子节点
     * @param parentId
     * @return
     */
    public List<Book> selectChildBooks(@Param("parentId") Long parentId);

    /**
     * 获取指定book 带child
     * @param code
     * @return
     */
    public Book selectBook(@Param("code") String code);
}
